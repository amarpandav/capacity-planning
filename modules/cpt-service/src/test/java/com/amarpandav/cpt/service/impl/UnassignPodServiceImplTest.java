package com.amarpandav.cpt.service.impl;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.availability.AvailabilityType;
import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.domain.entity.pod.PodAssignment;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.infra.exception.PodNotFoundException;
import com.amarpandav.cpt.service.dto.AssignmentsRequest;
import com.amarpandav.cpt.service.dto.TimeSlot;
import com.amarpandav.cpt.service.repository.PodAssignmentRepository;
import com.amarpandav.cpt.service.repository.PodRepository;
import com.amarpandav.cpt.testdata.pod.PodTestdata;
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
        when(podAssignmentRepositoryMock.getPodAssignment(any(), any(), any(), any())).thenReturn(
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
                        USER_IDS, START_DATE, START_DATE.plusMonths(13), TimeSlot.MORNING, TimeSlot.AFTERNOON)));
    }

    private void executeService(EntityId<Pod> podId) {
        service.execute(new AssignmentsRequest(podId.getUuid(),
                USER_IDS, START_DATE, END_DATE, TimeSlot.AFTERNOON, TimeSlot.MORNING));
    }
}