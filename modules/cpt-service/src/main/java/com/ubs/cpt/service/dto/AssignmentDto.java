package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;

import java.time.LocalDate;

public record AssignmentDto(
        LocalDate day,
        AvailabilityType morningAvailability,
        AvailabilityType eveningAvailability) implements Comparable<AssignmentDto> {
    public static AssignmentDto available(LocalDate date) {
        return new AssignmentDto(date, AvailabilityType.AVAILABLE, AvailabilityType.AVAILABLE);
    }

    @Override
    public int compareTo(AssignmentDto o) {
        return day.compareTo(o.day);
    }
}
