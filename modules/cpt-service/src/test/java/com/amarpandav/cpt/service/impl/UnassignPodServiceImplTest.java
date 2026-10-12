package com.amarpandav.cpt.service.impl;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.availability.AvailabilityType;
import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.domain.entity.pod.PodAssignment;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.infra.exception.NotPodLeadException;
import com.amarpandav.cpt.infra.exception.PodNotFoundException;
import com.amarpandav.cpt.service.dto.AssignmentsRequest;
import com.amarpandav.cpt.service.dto.SkippedPodDto;
import com.amarpandav.cpt.service.dto.TimeSlot;
import com.amarpandav.cpt.service.dto.UnassignPodResult;
import com.amarpandav.cpt.service.repository.PodAssignmentRepository;
import com.amarpandav.cpt.service.repository.PodRepository;
import com.amarpandav.cpt.testdata.pod.PodTestdata;
import com.amarpandav.cpt.testdata.user.UserTestdata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UnassignPodServiceImplTest {

    private static final Set<String> USER_IDS = Set.of("123", "456");

    private static final LocalDate
            START_DATE = LocalDate.of(2024, 9, 2),
            END_DATE = LocalDate.of(2024, 9, 4);
    public static final User USER_1 = new User("Liam", new UserKey("456123"));
    public static final User USER_2 = new User("Amar", new UserKey("123456"));
    // ORION's pod lead
    private static final UserKey TARA = UserTestdata.suiteSynthetic().TARA.getKey();
    // ATLAS's pod lead; member of ORION, but not its pod lead
    private static final UserKey AMAR = UserTestdata.suiteSynthetic().AMAR.getKey();

    private UnassignPodServiceImpl service;
    private PodAssignmentRepository podAssignmentRepositoryMock;
    private PodRepository podRepositoryMock;

    @BeforeEach
    public void setUp() {
        podAssignmentRepositoryMock = mock(PodAssignmentRepository.class);
        podRepositoryMock = mock(PodRepository.class);
        service = new UnassignPodServiceImpl(podAssignmentRepositoryMock, podRepositoryMock);
    }

    @Test
    public void whenFullDayInput_thenUnassignsCorrectly() {
        Pod orion = PodTestdata.suiteSynthetic().ORION;
        EntityId<Pod> podId = orion.getEntityId();
        when(podRepositoryMock.findById(eq(podId))).thenReturn(Optional.of(orion));
        Pod atlas = PodTestdata.suiteSynthetic().ATLAS;
        when(podAssignmentRepositoryMock.getPodAssignment(any(), any(), any())).thenReturn(
                List.of(
                        new PodAssignment(START_DATE, USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, orion, atlas),
                        new PodAssignment(START_DATE.plusDays(1), USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, atlas, orion),
                        new PodAssignment(END_DATE, USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, orion, orion),
                        new PodAssignment(START_DATE, USER_2, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, orion, atlas),
                        new PodAssignment(START_DATE.plusDays(1), USER_2, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, orion, atlas),
                        new PodAssignment(END_DATE, USER_2, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, orion, atlas)
                )
        );

        executeService(podId);

        ArgumentCaptor<List<PodAssignment>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(podAssignmentRepositoryMock, times(1)).saveAll(argumentCaptor.capture());
        List<PodAssignment> savedAssignments = argumentCaptor.getValue();
        assertEquals(6, savedAssignments.size());
        assertEquals(3, savedAssignments.stream()
                .filter(pa -> pa.getMorningAvailabilityType().isAvailable())
                .toList().size());
        assertEquals(1, savedAssignments.stream()
                .filter(pa -> pa.getAfternoonAvailabilityType().isAvailable())
                .toList().size());
        assertEquals(1, savedAssignments.stream()
                .filter(pa -> atlas.equals(pa.getMorningPod()))
                .toList().size());
        assertEquals(4, savedAssignments.stream()
                .filter(pa -> atlas.equals(pa.getAfternoonPod()))
                .toList().size());
    }

    @Test
    void whenOtherPodsInRange_thenReportsThemWithTheirPodLeads() {
        Pod atlas = PodTestdata.suiteSynthetic().ATLAS;
        Pod orion = PodTestdata.suiteSynthetic().ORION;
        when(podRepositoryMock.findById(eq(atlas.getEntityId()))).thenReturn(Optional.of(atlas));
        when(podAssignmentRepositoryMock.getPodAssignment(any(), any(), any())).thenReturn(
                List.of(
                        new PodAssignment(START_DATE, USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, orion, atlas),
                        new PodAssignment(START_DATE.plusDays(1), USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, atlas, orion)
                )
        );

        UnassignPodResult result = executeService(atlas.getEntityId(), AMAR);

        assertEquals(List.of(new SkippedPodDto(orion.getEntityId().getUuid(), "Orion Platform", "ORION", List.of("Tara Stone"))),
                result.skippedPods());
    }

    @Test
    void whenOtherPodOnlyOutsideHalfDayRange_thenNothingReported() {
        Pod atlas = PodTestdata.suiteSynthetic().ATLAS;
        Pod orion = PodTestdata.suiteSynthetic().ORION;
        when(podRepositoryMock.findById(eq(atlas.getEntityId()))).thenReturn(Optional.of(atlas));
        // range starts on START_DATE afternoon and ends on END_DATE morning, so these ORION slots are outside it
        when(podAssignmentRepositoryMock.getPodAssignment(any(), any(), any())).thenReturn(
                List.of(
                        new PodAssignment(START_DATE, USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, orion, atlas),
                        new PodAssignment(END_DATE, USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, atlas, orion)
                )
        );

        UnassignPodResult result = executeService(atlas.getEntityId(), AMAR);

        assertEquals(List.of(), result.skippedPods());
    }

    @Test
    void whenPodNotFound_thenThrowException() {
        EntityId<Pod> podId = EntityId.create();
        when(podRepositoryMock.findById(eq(podId))).thenReturn(Optional.empty());
        assertThrows(PodNotFoundException.class,
                () -> executeService(podId));
    }

    @Test
    void whenPeriodLongerThanYear_thenThrowException() {
        EntityId<Pod> podId = EntityId.create();
        assertThrows(IllegalArgumentException.class,
                () -> service.execute(new AssignmentsRequest(podId.getUuid(),
                        USER_IDS, START_DATE, START_DATE.plusMonths(13), TimeSlot.MORNING, TimeSlot.AFTERNOON), TARA));
    }

    @Test
    void whenActingUserIsNotPodLead_thenThrowsAndSavesNothing() {
        Pod orion = PodTestdata.suiteSynthetic().ORION;
        when(podRepositoryMock.findById(eq(orion.getEntityId()))).thenReturn(Optional.of(orion));

        NotPodLeadException ex = assertThrows(NotPodLeadException.class, () -> executeService(orion.getEntityId(), AMAR));

        assertEquals("ORION", ex.getPodShortName());
        assertEquals(List.of("Tara Stone"), ex.getPodLeadNames());
        verify(podAssignmentRepositoryMock, never()).saveAll(any());
    }

    private UnassignPodResult executeService(EntityId<Pod> podId) {
        return executeService(podId, TARA);
    }

    private UnassignPodResult executeService(EntityId<Pod> podId, UserKey actingUser) {
        return service.execute(new AssignmentsRequest(podId.getUuid(),
                USER_IDS, START_DATE, END_DATE, TimeSlot.AFTERNOON, TimeSlot.MORNING), actingUser);
    }
}