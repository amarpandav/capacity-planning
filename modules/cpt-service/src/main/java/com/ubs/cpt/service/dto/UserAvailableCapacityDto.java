package com.ubs.cpt.service.dto;

import java.time.LocalDate;

public record UserAvailableCapacityDto(
        String uuid,
        LocalDate day,
        UserDto user,
        AvailabilityDto morningAvailability,
        AvailabilityDto afternoonAvailability
) {
}
