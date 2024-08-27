package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;

import java.time.LocalDate;
import java.util.Objects;

public record AssignmentDto(
        LocalDate day,
        AvailabilityType morningAvailability,
        AvailabilityType eveningAvailability) {
    public static AssignmentDto available(LocalDate date) {
        return new AssignmentDto(date, AvailabilityType.AVAILABLE, AvailabilityType.AVAILABLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentDto that = (AssignmentDto) o;
        return Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day);
    }
}
