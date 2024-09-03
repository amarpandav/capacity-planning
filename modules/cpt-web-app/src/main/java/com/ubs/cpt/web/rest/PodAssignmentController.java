package com.ubs.cpt.web.rest;

import com.ubs.cpt.service.CreateAssignmentsService;
import com.ubs.cpt.service.PodAssignmentService;
import com.ubs.cpt.service.dto.PodAssignmentsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/pods")
public class PodAssignmentController {

    private final PodAssignmentService podAssignmentService;
    private final CreateAssignmentsService createAssignmentsService;

    public PodAssignmentController(PodAssignmentService podAssignmentService,
                                   CreateAssignmentsService createAssignmentsService) {
        this.podAssignmentService = podAssignmentService;
        this.createAssignmentsService = createAssignmentsService;
    }

    @GetMapping("/{id}/assignments")
    public ResponseEntity<PodAssignmentsResponse> getPodAssignments(@PathVariable("id") String podId,
                                                                    @RequestParam("startDate") LocalDate startDate,
                                                                    @RequestParam("endDate") LocalDate endDate) {

        return ResponseEntity.ok(podAssignmentService.getPodAssignment(
                new PodAssignmentService.PodAssignmentRequest(podId, startDate, endDate)));
    }

    // debugging purposes
    // TODO ultra slow and OutOfMemoryError: Java heap space
    // check implementation
    @GetMapping("/{id}/all-assignments")
    public ResponseEntity<PodAssignmentsResponse> getAllPodAssignments(@PathVariable("id") String podId) {

        return ResponseEntity.ok(podAssignmentService.getPodAssignment(
                        new PodAssignmentService.PodAssignmentRequest(
                                podId,
                                LocalDate.MIN,
                                LocalDate.MAX.minusDays(1)
                        )
                )
        );
    }

    @PostMapping("/{id}/assignments")
    public ResponseEntity createAssignments(
            @PathVariable("id") String podId,
            @RequestBody CreateAssignmentsService.CreateAssignmentsRequest request) {
        createAssignmentsService.execute(new CreateAssignmentsService.CreateAssignmentsRequest(
                podId, request.userIds(), request.startDate(), request.endDate(), request.startTimeSlot(), request.endTimeSlot()
        ));
        return ResponseEntity.ok().build();
    }
}
