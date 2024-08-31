package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.exception.PodNotFoundException;
import com.ubs.cpt.service.CreateAssignmentsService;
import com.ubs.cpt.service.repository.PodAssignmentRepository;
import com.ubs.cpt.service.repository.PodMemberRepository;
import com.ubs.cpt.service.repository.PodRepository;
import com.ubs.cpt.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class CreateAssignmentsServiceImpl implements CreateAssignmentsService {

    private final PodAssignmentRepository podAssignmentRepository;
    private final PodRepository podRepository;
    private final PodMemberRepository podMemberRepository;
    private final UserRepository userRepository;

    public CreateAssignmentsServiceImpl(PodAssignmentRepository podAssignmentRepository,
                                        PodRepository podRepository, PodMemberRepository podMemberRepository,
                                        UserRepository userRepository) {
        this.podAssignmentRepository = podAssignmentRepository;
        this.podRepository = podRepository;
        this.podMemberRepository = podMemberRepository;
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
                                .map(pa -> updatePodAssignment(request, pod, startDate, endDate, day, pa))
                                .findFirst()
                                .orElseGet(() -> {
                                    PodAssignment newPodAssignment = new PodAssignment(day, user, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, pod, pod);
                                    newPodAssignment.prePersist();
                                    return newPodAssignment;
                                })
                        )
                        .toList())
                .flatMap(Collection::stream)
                .toList();

        podAssignmentRepository.saveAll(podAssignmentsToSave);
        log.info("updated/created {} pod assignments", podAssignmentsToSave.size());
    }

    private PodAssignment updatePodAssignment(CreateAssignmentsRequest request, Pod pod, LocalDate startDate, LocalDate endDate, LocalDate day, PodAssignment pa) {
        updateMorning(request, pod, startDate, day, pa);
        updateAfternoon(request, pod, endDate, day, pa);
        return pa;
    }

    private void updateMorning(CreateAssignmentsRequest request, Pod pod, LocalDate startDate, LocalDate day, PodAssignment pa) {
        if (!day.equals(startDate) || isNull(request.startTimeSlot()) || request.startTimeSlot().isMorning()) {
            pa.setMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                    .setMorningPod(pod);
        }
    }

    private void updateAfternoon(CreateAssignmentsRequest request, Pod pod, LocalDate endDate, LocalDate day, PodAssignment pa) {
        if (!day.equals(endDate) || isNull(request.endTimeSlot()) || request.endTimeSlot().isAfternoon()) {
            pa.setAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                    .setAfternoonPod(pod);
        }
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
