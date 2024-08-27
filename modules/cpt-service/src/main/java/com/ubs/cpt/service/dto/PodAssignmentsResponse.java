package com.ubs.cpt.service.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record PodAssignmentsResponse(Set<UserAssignmentDto> assignments) {

    public static PodAssignmentsResponse available(List<String> userIds, LocalDate startDate, LocalDate endDate) {
        return new PodAssignmentsResponse(
                userIds.stream()
                        .map(userId -> new UserAssignmentDto(userId, startDate, endDate))
                        .collect(Collectors.toSet())
        );
    }

    public void add(String userId, List<AssignmentDto> assignmentDtos) {
        assignments.stream()
                .filter(ua -> ua.getUserId().equals(userId))
                .findAny()
                .ifPresent(assignment -> assignment.add(assignmentDtos));
    }

}
