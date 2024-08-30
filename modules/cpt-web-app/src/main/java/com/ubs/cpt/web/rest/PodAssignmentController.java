package com.ubs.cpt.web.rest;

import com.ubs.cpt.service.PodAssignmentService;
import com.ubs.cpt.service.dto.PodAssignmentsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
