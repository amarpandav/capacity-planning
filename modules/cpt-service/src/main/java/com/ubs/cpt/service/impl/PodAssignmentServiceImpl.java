package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.domain.entity.pod.PodMember;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.exception.PodNotFoundException;
import com.ubs.cpt.service.PodAssignmentService;
import com.ubs.cpt.service.dto.AssignmentDto;
import com.ubs.cpt.service.dto.PodAssignmentsResponse;
import com.ubs.cpt.service.repository.PodAssignmentRepository;
import com.ubs.cpt.service.repository.PodMemberRepository;
import com.ubs.cpt.service.repository.PodRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PodAssignmentServiceImpl implements PodAssignmentService {

    private final PodRepository podRepository;
    private final PodMemberRepository podMemberRepository;
    private final PodAssignmentRepository podAssignmentRepository;

    public PodAssignmentServiceImpl(PodRepository podRepository,
                                    PodMemberRepository podMemberRepository,
                                    PodAssignmentRepository podAssignmentRepository) {
        this.podRepository = podRepository;
        this.podMemberRepository = podMemberRepository;
        this.podAssignmentRepository = podAssignmentRepository;
    }

    @Override
    public PodAssignmentsResponse getPodAssignment(PodAssignmentRequest request) {
        String podId = request.podId();
        Pod pod = podRepository.findById(new EntityId<>(podId))
                .orElseThrow(() -> new PodNotFoundException("pod not found by id " + podId));

        List<String> userIds = podMemberRepository.findByPodId(podId).stream()
                .map(PodMember::getUser)
                .map(User::getEntityId)
                .map(EntityId::getUuid)
                .toList();
        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();
        List<PodAssignment> podAssignments = podAssignmentRepository.getPodAssignment(podId, startDate,
                endDate);
        PodAssignmentsResponse response = PodAssignmentsResponse.available(userIds, startDate, endDate);
        Map<String, List<PodAssignment>> assignmentsByUserId = podAssignments.stream()
                .collect(Collectors.groupingBy(podAssignment -> podAssignment.getUser().getEntityId().getUuid()));
        assignmentsByUserId.entrySet().forEach(entry -> {
            response.add(entry.getKey(), entry.getValue().stream()
                    .map(val -> new AssignmentDto(val.getDay(), val.getMorningAvailabilityType(), val.getAfternoonAvailabilityType()))
                    .toList());
        });
        return response;
    }
}
