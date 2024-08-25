package com.ubs.cpt.service.dto;

import java.time.LocalDate;

//TODO: Amar feedback: merge this dto into single dto PodAssignmentDto
public record UserBookedCapacityDto(
        String uuid,
        LocalDate day,
        UserDto user,
        PodDto morningPod,
        PodDto afternoonPod
) {
}
