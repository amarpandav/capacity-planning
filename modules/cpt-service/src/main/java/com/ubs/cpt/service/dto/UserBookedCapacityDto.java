package com.ubs.cpt.service.dto;

import java.time.LocalDate;

public record UserBookedCapacityDto(
        String uuid,
        LocalDate day,
        UserDto user,
        PodDto morningPod,
        PodDto afternoonPod
) {
}
