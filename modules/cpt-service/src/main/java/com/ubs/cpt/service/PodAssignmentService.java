package com.ubs.cpt.service;

import com.ubs.cpt.service.dto.PodAssignmentsResponse;

import java.time.LocalDate;

public interface PodAssignmentService {

    PodAssignmentsResponse getPodAssignment(PodAssignmentRequest request);

    record PodAssignmentRequest(String podId, LocalDate startDate, LocalDate endDate) {}
}
