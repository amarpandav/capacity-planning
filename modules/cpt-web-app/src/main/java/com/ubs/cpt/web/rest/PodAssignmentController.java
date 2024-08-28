package com.ubs.cpt.web.rest;

import com.ubs.cpt.service.PodAssignmentService;
import com.ubs.cpt.service.dto.PodAssignmentsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class PodAssignmentController {

    private final PodAssignmentService service;

    public PodAssignmentController(PodAssignmentService service) {
        this.service = service;
    }

    @GetMapping("/pods/{id}/assignments")
    public ResponseEntity<PodAssignmentsResponse> getPodAssignments(@PathVariable("id") String podId,
                                                                    @RequestParam("startDate") LocalDate startDate,
                                                                    @RequestParam("endDate") LocalDate endDate) {

        return ResponseEntity.ok(service.getPodAssignment(
                new PodAssignmentService.PodAssignmentRequest(podId, startDate, endDate)));
    }
}
