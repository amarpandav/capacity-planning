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
import com.amarpandav.cpt.service.dto.TimeSlot;
import com.amarpandav.cpt.service.repository.PodAssignmentRepository;
import com.amarpandav.cpt.service.repository.PodRepository;
import com.amarpandav.cpt.service.repository.UserRepository;
import com.amarpandav.cpt.testdata.pod.PodTestdata;
import com.amarpandav.cpt.testdata.user.UserTestdata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CreateAssignmentsServiceImplTest {

    private static final Set<String> USER_IDS = Set.of("123", "456");

    private static final LocalDate
            START_DATE = LocalDate.of(2024, 9, 2),
            END_DATE = LocalDate.of(2024, 9, 3);
    public static final User USER_1 = new User("Liam", new UserKey("456123"));
    public static final User USER_2 = new User("Amar", new UserKey("123456"));
    // ORION's pod lead
    private static final UserKey TARA = UserTestdata.suiteSynthetic().TARA.getKey();
    // member of ORION, but not its pod lead
    private static final UserKey AMAR = UserTestdata.suiteSynthetic().AMAR.getKey();

    private CreateAssignmentsServiceImpl service;
    private PodAssignmentRepository podAssignmentRepositoryMock;
    private PodRepository podRepositoryMock;
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setUp() {
        podAssignmentRepositoryMock = mock(PodAssignmentRepository.class);
        podRepositoryMock = mock(PodRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        service = new CreateAssignmentsServiceImpl(podAssignmentRepositoryMock, podRepositoryMock, userRepositoryMock);
    }

    @Test
    void whenPodNotFound_thenThrowException() {
        EntityId<Pod> podId = EntityId.create();
        when(podRepositoryMock.findById(eq(podId))).thenReturn(Optional.empty());
        assertThrows(PodNotFoundException.class,
                () -> service.execute(new AssignmentsRequest(podId.getUuid(),
                        Collections.emptySet(), START_DATE, END_DATE, TimeSlot.MORNING, TimeSlot.AFTERNOON), TARA));
    }

    @Test
    void whenPeriodLongerThanYear_thenThrowException() {
        EntityId<Pod> podId = EntityId.create();
        assertThrows(IllegalArgumentException.class,
                () -> service.execute(new AssignmentsRequest(podId.getUuid(),
                        Collections.emptySet(), START_DATE, START_DATE.plusMonths(13), TimeSlot.MORNING, TimeSlot.AFTERNOON), TARA));
    }

    @Test
    void whenPodAssignmentsNotFound_createNewInDb() {
        EntityId<Pod> podId = EntityId.create();
        when(podRepositoryMock.findById(eq(podId))).thenReturn(Optional.of(PodTestdata.suiteSynthetic().ORION));
        when(podAssignmentRepositoryMock.getPodAssignment(any(), any(), any())).thenReturn(Collections.emptyList());
        List<User> foundUsers = List.of(USER_1, USER_2);
        when(userRepositoryMock.findAllById(any())).thenReturn(foundUsers);

        executeService(podId);

        ArgumentCaptor<List<PodAssignment>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(podAssignmentRepositoryMock, times(1)).saveAll(argumentCaptor.capture());
        int usersTimesDays = foundUsers.size() * START_DATE.until(END_DATE.plusDays(1)).getDays();
        assertEquals(usersTimesDays, argumentCaptor.getValue().size());
    }

    @Test
    void whenPodAssignmentsFoundAndHalfBooked_saveToDb() {
        EntityId<Pod> podId = EntityId.create();
        when(podRepositoryMock.findById(eq(podId))).thenReturn(Optional.of(PodTestdata.suiteSynthetic().ORION));
        when(podAssignmentRepositoryMock.getPodAssignment(any(), any(), any())).thenReturn(
                List.of(
                        new PodAssignment(START_DATE, USER_1, AvailabilityType.AVAILABLE, AvailabilityType.POD_ASSIGNMENT, null, PodTestdata.suiteSynthetic().ATLAS),
                        new PodAssignment(END_DATE, USER_1, AvailabilityType.AVAILABLE, AvailabilityType.POD_ASSIGNMENT, null, PodTestdata.suiteSynthetic().ATLAS),
                        new PodAssignment(START_DATE, USER_2, AvailabilityType.AVAILABLE, AvailabilityType.POD_ASSIGNMENT, null, PodTestdata.suiteSynthetic().ATLAS),
                        new PodAssignment(END_DATE, USER_2, AvailabilityType.AVAILABLE, AvailabilityType.POD_ASSIGNMENT, null, PodTestdata.suiteSynthetic().ATLAS)
                )
        );
        List<User> foundUsers = List.of(USER_1, USER_2);
        when(userRepositoryMock.findAllById(any())).thenReturn(foundUsers);

        executeService(podId);

        ArgumentCaptor<List<PodAssignment>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(podAssignmentRepositoryMock, times(1)).saveAll(argumentCaptor.capture());
        int usersTimesDays = foundUsers.size() * START_DATE.until(END_DATE.plusDays(1)).getDays();
        assertEquals(usersTimesDays, argumentCaptor.getValue().size());
    }

    @Test
    void whenPodAssignmentsFoundButFullyBooked_noSave() {
        EntityId<Pod> podId = EntityId.create();
        when(podRepositoryMock.findById(eq(podId))).thenReturn(Optional.of(PodTestdata.suiteSynthetic().ORION));
        when(podAssignmentRepositoryMock.getPodAssignment(any(), any(), any())).thenReturn(
                List.of(
                        new PodAssignment(START_DATE, USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, PodTestdata.suiteSynthetic().ATLAS, PodTestdata.suiteSynthetic().ATLAS),
                        new PodAssignment(END_DATE, USER_1, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, PodTestdata.suiteSynthetic().ATLAS, PodTestdata.suiteSynthetic().ATLAS),
                        new PodAssignment(START_DATE, USER_2, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, PodTestdata.suiteSynthetic().ATLAS, PodTestdata.suiteSynthetic().ATLAS),
                        new PodAssignment(END_DATE, USER_2, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, PodTestdata.suiteSynthetic().ATLAS, PodTestdata.suiteSynthetic().ATLAS)
                )
        );
        List<User> foundUsers = List.of(USER_1, USER_2);
        when(userRepositoryMock.findAllById(any())).thenReturn(foundUsers);

        executeService(podId);

        ArgumentCaptor<List<PodAssignment>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(podAssignmentRepositoryMock, times(1)).saveAll(argumentCaptor.capture());
        assertEquals(0, argumentCaptor.getValue().size());
    }

    @Test
    void whenActingUserIsNotPodLead_thenThrowsAndSavesNothing() {
        Pod orion = PodTestdata.suiteSynthetic().ORION;
        when(podRepositoryMock.findById(eq(orion.getEntityId()))).thenReturn(Optional.of(orion));

        NotPodLeadException ex = assertThrows(NotPodLeadException.class, () -> service.execute(new AssignmentsRequest(
                orion.getEntityId().getUuid(), USER_IDS, START_DATE, END_DATE, TimeSlot.MORNING, TimeSlot.AFTERNOON), AMAR));

        assertEquals("ORION", ex.getPodShortName());
        assertEquals(List.of("Tara Stone"), ex.getPodLeadNames());
        verify(podAssignmentRepositoryMock, never()).saveAll(any());
    }

    private void executeService(EntityId<Pod> podId) {
        service.execute(new AssignmentsRequest(podId.getUuid(),
                USER_IDS, START_DATE, END_DATE, TimeSlot.MORNING, TimeSlot.AFTERNOON), TARA);
    }
}