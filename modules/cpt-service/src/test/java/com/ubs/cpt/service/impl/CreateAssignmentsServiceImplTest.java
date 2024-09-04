package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.exception.PodNotFoundException;
import com.ubs.cpt.service.CreateAssignmentsService;
import com.ubs.cpt.service.repository.PodAssignmentRepository;
import com.ubs.cpt.service.repository.PodRepository;
import com.ubs.cpt.service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateAssignmentsServiceImplTest {

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
                () -> service.execute(new CreateAssignmentsService.CreateAssignmentsRequest(podId.getUuid(),
                        Collections.emptySet(), LocalDate.now(), LocalDate.now(), null, null)));
    }
// TODO add some more
}