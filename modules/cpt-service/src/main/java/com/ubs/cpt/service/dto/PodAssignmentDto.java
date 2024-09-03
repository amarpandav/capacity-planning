package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;

import java.time.LocalDate;
import java.util.Objects;

public record PodAssignmentDto(
        String uuid,
        LocalDate day,
        AssignmentDto morning,
        AssignmentDto afternoon) {
    public static PodAssignmentDto available(LocalDate date) {
        return new PodAssignmentDto("virtual", date, AssignmentDto.available(), AssignmentDto.available());
    }

    public static PodAssignmentDto publicHoliday(LocalDate day) {
        return new PodAssignmentDto("virtual", day, AssignmentDto.publicHoliday(), AssignmentDto.publicHoliday());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PodAssignmentDto that = (PodAssignmentDto) o;
        return Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day);
    }

    public record AssignmentDto(AvailabilityType availabilityType, PodDto pod) {
        public static AssignmentDto available() {
            return new AssignmentDto(AvailabilityType.AVAILABLE, null);
        }

        public static AssignmentDto publicHoliday() {
            return new AssignmentDto(AvailabilityType.PUBLIC_HOLIDAY, null);
        }
    }
}
