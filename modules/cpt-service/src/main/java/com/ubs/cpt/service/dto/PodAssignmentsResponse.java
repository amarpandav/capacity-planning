package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.entity.pod.PodMember;

import java.time.LocalDate;
import java.util.List;

public record PodAssignmentsResponse(List<UserAssignmentDto> assignments) {

    public static PodAssignmentsResponse available(List<PodMember> podMembers, LocalDate startDate, LocalDate endDate) {
        return new PodAssignmentsResponse(
                podMembers.stream()
                        .map(podMember -> new UserAssignmentDto(podMember.getUser(), podMember.getPodMemberRole(), startDate, endDate))
                        .toList()
        );
    }

    public void add(String userId, List<AssignmentDto> assignmentDtos) {
        assignments.stream()
                .filter(userAssignment -> userAssignment.getUser().getEntityId().getUuid().equals(userId))
                .findAny()
                .ifPresent(assignment -> assignment.add(assignmentDtos));
    }

}
