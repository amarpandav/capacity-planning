package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.domain.entity.pod.PodMember;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.exception.PodNotFoundException;
import com.ubs.cpt.service.CreateAssignmentsService;
import com.ubs.cpt.service.repository.PodAssignmentRepository;
import com.ubs.cpt.service.repository.PodMemberRepository;
import com.ubs.cpt.service.repository.PodRepository;
import com.ubs.cpt.service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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

    // TODO correct the logic, consider startTimeSlot and endTimeSlot
    @Override
    public void execute(CreateAssignmentsRequest request) {
        String podId = request.podId();
        Pod pod = podRepository.findById(new EntityId<>(podId))
                .orElseThrow(() -> new PodNotFoundException("pod not found by id " + podId));

//        Set<String> userIds = podMemberRepository.findByPodId(podId).stream()
//                .map(PodMember::getUser)
//                .map(User::getEntityId)
//                .map(EntityId::getUuid)
//                .collect(Collectors.toSet());
        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();
        List<PodAssignment> existingPodAssignments = podAssignmentRepository.getPodAssignment(request.userIds(), startDate,
                endDate);
        List<EntityId<User>> userEntityIds = request.userIds().stream()
                .distinct()
                .map(id -> new EntityId<User>(id))
                .toList();

        List<PodAssignment> podAssignmentsToSave = userRepository.findAllById(userEntityIds).stream()
                .map(user -> request.startDate().datesUntil(endDate.plusDays(1))
                        .filter(day -> existingPodAssignments.stream()
                                .noneMatch(podAssignment -> podAssignment.getUser().getEntityId().equals(user.getEntityId())
                                        && podAssignment.getDay().equals(day)
                                        && !podAssignment.getMorningAvailabilityType().isAvailable()
                                        && !podAssignment.getAfternoonAvailabilityType().isAvailable()))
                        .map(day -> existingPodAssignments.stream()
                                .filter(pa -> pa.getDay().equals(day) && pa.getUser().getEntityId().equals(user.getEntityId()))
                                .map(pa -> {
                                    PodAssignment updated = new PodAssignment(day, user, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.POD_ASSIGNMENT, pod, pod);
                                    updated.setEntityId(pa.getEntityId());
                                    updated.setAuditInfo(pa.getAuditInfo());
                                    return updated;
                                })
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
    }
}
