package com.ubs.cpt.service.dto;

import java.time.LocalDate;
import java.util.*;

public class UserAssignmentDto {
    private final String userId;
    private final Set<AssignmentDto> assignments = new HashSet<>();

    public UserAssignmentDto(String userId) {
        this.userId = userId;
    }

    public UserAssignmentDto(String userId, LocalDate startDate, LocalDate endDate) {
        this(userId);
        startDate.datesUntil(endDate.plusDays(1))
                .map(AssignmentDto::available)
                .forEach(assignments::add);
    }

    public String getUserId() {
        return userId;
    }

    public Set<AssignmentDto> getAssignments() {
        return assignments;
    }

    public void add(List<AssignmentDto> toAdd) {
        assignments.addAll(toAdd);
    }

}
