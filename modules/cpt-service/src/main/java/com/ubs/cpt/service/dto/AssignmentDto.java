package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;

import java.time.LocalDate;
import java.util.Objects;

public record AssignmentDto(
        String uuid,
        LocalDate day,
        AvailabilityType morningAvailability,
        AvailabilityType eveningAvailability) {
    public static AssignmentDto available(LocalDate date) {
        return new AssignmentDto("virtual", date, AvailabilityType.AVAILABLE, AvailabilityType.AVAILABLE);
    }

    public static AssignmentDto publicHoliday(LocalDate day) {
        return new AssignmentDto("virtual", day, AvailabilityType.PUBLIC_HOLIDAY, AvailabilityType.PUBLIC_HOLIDAY);
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
