package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
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
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        validate(request);
        String podId = request.podId();
        podRepository.findById(new EntityId<>(podId))
                .orElseThrow(() -> new PodNotFoundException("pod not found by id " + podId));

        List<PodMember> podMembers = podMemberRepository.findByPodId(podId);
        Set<String> userIds = podMembers.stream()
                .map(PodMember::getUser)
                .map(User::getEntityId)
                .map(EntityId::getUuid)
                .collect(Collectors.toSet());
        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();
        List<PodAssignment> podAssignments = podAssignmentRepository.getPodAssignment(userIds, startDate,
                endDate);
        PodAssignmentsResponse response = PodAssignmentsResponse.available(podMembers, startDate, endDate);
        Map<String, List<PodAssignment>> assignmentsByUserId = podAssignments.stream()
                .collect(Collectors.groupingBy(podAssignment -> podAssignment.getUser().getEntityId().getUuid()));
        assignmentsByUserId.forEach((key, value) -> response.add(key, value.stream()
                .map(val -> new AssignmentDto(val.getEntityId().getUuid(), val.getDay(), val.getMorningAvailabilityType(), val.getAfternoonAvailabilityType()))
                .toList()));
        return response;
    }

    private void validate(PodAssignmentRequest request) {
        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();

        Period duration = startDate.until(endDate);
        if (duration.toTotalMonths() > 12) {
            throw new IllegalArgumentException("duration longer than 12 months between startDate and endDate is not supported");
        }
    }
}
