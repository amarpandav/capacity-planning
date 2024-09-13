package com.ubs.cpt.service.dto;

import java.time.LocalDate;
import java.util.Set;

public record AssignmentsRequest(
        String podId,
        Set<String> userIds,
        LocalDate startDate,
        LocalDate endDate,
        TimeSlot startTimeSlot,
        TimeSlot endTimeSlot
) {
}
