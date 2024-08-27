package com.ubs.cpt.service.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record PodAssignmentsResponse(List<UserAssignmentDto> assignments) {

    public static PodAssignmentsResponse available(Set<String> userIds, LocalDate startDate, LocalDate endDate) {
        return new PodAssignmentsResponse(
                userIds.stream()
                        .map(userId -> new UserAssignmentDto(userId, startDate, endDate))
                        .toList()
        );
    }

    public void add(String userId, List<AssignmentDto> assignmentDtos) {
        assignments.stream()
                .filter(userAssignment -> userAssignment.getUserId().equals(userId))
                .findAny()
                .ifPresent(assignment -> assignment.add(assignmentDtos));
    }

}
