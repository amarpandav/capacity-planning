package com.ubs.cpt.service.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserAssignmentDto {
    private final String userId;
    private final Set<AssignmentDto> assignments = new HashSet<>();

    public UserAssignmentDto(String userId) {
        this.userId = userId;
    }

    public UserAssignmentDto(String userId, LocalDate startDate, LocalDate endDate) {
        this(userId);
        startDate.datesUntil(endDate.plusDays(1))
                .map(day -> {
                    if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        return AssignmentDto.publicHoliday(day);
                    } else {
                        return AssignmentDto.available(day);
                    }
                })
                .forEach(assignments::add);
    }

    public String getUserId() {
        return userId;
    }

    public List<AssignmentDto> getAssignments() {
        return assignments.stream()
                .sorted(Comparator.comparing(AssignmentDto::day))
                .toList();
    }

    public void add(List<AssignmentDto> toAdd) {
        toAdd.forEach(input -> {
            if (assignments.remove(input)) {
                assignments.add(input);
            }
        });
    }

}
