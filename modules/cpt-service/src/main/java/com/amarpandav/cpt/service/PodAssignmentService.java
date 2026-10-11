package com.amarpandav.cpt.service;

import com.amarpandav.cpt.service.dto.PodAssignmentsResponse;

import java.time.LocalDate;

public interface PodAssignmentService {

    PodAssignmentsResponse getPodAssignment(PodAssignmentRequest request);

    record PodAssignmentRequest(String podId, LocalDate startDate, LocalDate endDate) {}
}
