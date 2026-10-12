package com.amarpandav.cpt.web.rest;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.service.CreateAssignmentsService;
import com.amarpandav.cpt.service.PodAssignmentService;
import com.amarpandav.cpt.service.UnassignPodService;
import com.amarpandav.cpt.service.dto.AssignmentsRequest;
import com.amarpandav.cpt.service.dto.PodAssignmentsResponse;
import com.amarpandav.cpt.service.dto.UnassignPodResult;

@RestController
@RequestMapping("/pods")
public class PodAssignmentController {

    /**
     * Stand-in for real authentication (#16): the UI sends the acting user's employee ID and it is trusted as is.
     */
    static final String ACTING_EMPLOYEE_ID_HEADER = "X-Acting-Employee-Id";

    private final PodAssignmentService podAssignmentService;
    private final CreateAssignmentsService createAssignmentsService;
    private final UnassignPodService unassignPodService;

    public PodAssignmentController(PodAssignmentService podAssignmentService,
                                   CreateAssignmentsService createAssignmentsService,
                                   UnassignPodService unassignPodService) {
        this.podAssignmentService = podAssignmentService;
        this.createAssignmentsService = createAssignmentsService;
        this.unassignPodService = unassignPodService;
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
            @RequestHeader(ACTING_EMPLOYEE_ID_HEADER) String actingEmployeeId,
            @RequestBody AssignmentsRequest request) {
        createAssignmentsService.execute(new AssignmentsRequest(
                podId, request.userIds(), request.startDate(), request.endDate(), request.startTimeSlot(), request.endTimeSlot()
        ), new UserKey(actingEmployeeId));
        return ResponseEntity.ok().build();
    }

    /**
     * Unassigns the pod from the slots in the range. The response lists other pods found in those slots, which were
     * left untouched, together with their pod leads.
     */
    @PutMapping("/{id}/assignments")
    public ResponseEntity<UnassignPodResult> unassignPod(
            @PathVariable("id") String podId,
            @RequestHeader(ACTING_EMPLOYEE_ID_HEADER) String actingEmployeeId,
            @RequestBody AssignmentsRequest request
    ) {
        return ResponseEntity.ok(unassignPodService.execute(new AssignmentsRequest(
                podId, request.userIds(), request.startDate(), request.endDate(), request.startTimeSlot(), request.endTimeSlot()
        ), new UserKey(actingEmployeeId)));
    }
}
