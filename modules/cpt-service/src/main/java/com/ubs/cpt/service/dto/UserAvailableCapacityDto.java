package com.ubs.cpt.service.dto;

import java.time.LocalDate;

//TODO: Amar feedback: merge this dto into single dto PodAssignmentDto
public record UserAvailableCapacityDto(
        String uuid,
        LocalDate day,
        UserDto user,
        AvailabilityDto morningAvailability,
        AvailabilityDto afternoonAvailability
) {
}
