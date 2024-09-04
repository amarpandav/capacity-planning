package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.exception.PodNotFoundException;
import com.ubs.cpt.service.CreateAssignmentsService;
import com.ubs.cpt.service.repository.PodAssignmentRepository;
import com.ubs.cpt.service.repository.PodRepository;
import com.ubs.cpt.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class CreateAssignmentsServiceImpl implements CreateAssignmentsService {

    private final PodAssignmentRepository podAssignmentRepository;
    private final PodRepository podRepository;
    private final UserRepository userRepository;

    public CreateAssignmentsServiceImpl(PodAssignmentRepository podAssignmentRepository,
                                        PodRepository podRepository,
                                        UserRepository userRepository) {
        this.podAssignmentRepository = podAssignmentRepository;
        this.podRepository = podRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void execute(CreateAssignmentsRequest request) {
        log.info("creating pod assignments for request: {}", request);
        String podId = request.podId();
        Pod pod = podRepository.findById(new EntityId<>(podId))
                .orElseThrow(() -> new PodNotFoundException("pod not found by id " + podId));

        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();
        List<PodAssignment> existingPodAssignments = podAssignmentRepository.getPodAssignment(request.userIds(), startDate,
                endDate);

        List<PodAssignment> podAssignmentsToSave = getUsers(request).stream()
                .map(user -> startDate.datesUntil(endDate.plusDays(1))
                        .filter(day -> isNotFullyBookedThatDayAlready(existingPodAssignments, user, day))
                        .map(day -> existingPodAssignments.stream()
                                .filter(pa -> pa.getDay().equals(day) && pa.getUser().getEntityId().equals(user.getEntityId()))
                                .map(pa -> PodAssignmentUpdater.update(pa).with(request, pod))
                                .findFirst()
                                .orElseGet(() -> {
                                    PodAssignment newPodAssignment = new PodAssignment(day, user);
                                    newPodAssignment.prePersist();
                                    return PodAssignmentUpdater.update(newPodAssignment).with(request, pod);
                                })
                        )
                        .toList())
                .flatMap(Collection::stream)
                .toList();

        podAssignmentRepository.saveAll(podAssignmentsToSave);
        log.info("updated/created {} pod assignments", podAssignmentsToSave.size());
    }

    private boolean isNotFullyBookedThatDayAlready(List<PodAssignment> existingPodAssignments, User user, LocalDate day) {
        return existingPodAssignments.stream()
                .noneMatch(podAssignment -> podAssignment.getUser().getEntityId().equals(user.getEntityId())
                        && podAssignment.getDay().equals(day)
                        && podAssignment.getMorningAvailabilityType().isNotAvailable()
                        && podAssignment.getAfternoonAvailabilityType().isNotAvailable());
    }

    private List<User> getUsers(CreateAssignmentsRequest request) {
        return userRepository.findAllById(request.userIds().stream()
                .distinct()
                .map(id -> new EntityId<User>(id))
                .toList());
    }
}
