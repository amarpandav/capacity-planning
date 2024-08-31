package com.ubs.cpt.service;

import com.ubs.cpt.service.dto.TimeSlot;

import java.time.LocalDate;
import java.util.Set;

public interface CreateAssignmentsService {

    void execute(CreateAssignmentsRequest request);

    record CreateAssignmentsRequest(
            String podId,
            Set<String> userIds,
            LocalDate startDate,
            LocalDate endDate,
            TimeSlot startTimeSlot,
            TimeSlot endTimeSlot
            ) {

    }
}
