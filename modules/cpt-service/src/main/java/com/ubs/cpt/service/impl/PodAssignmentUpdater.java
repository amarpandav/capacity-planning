package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.service.dto.AssignmentsRequest;

import static java.util.Objects.isNull;

public class PodAssignmentUpdater {
    private final PodAssignment podAssignment;

    private PodAssignmentUpdater(PodAssignment podAssignment) {
        this.podAssignment = podAssignment;
    }

    public static PodAssignmentUpdater update(PodAssignment podAssignment) {
        return new PodAssignmentUpdater(podAssignment);
    }

    public PodAssignment with(AssignmentsRequest request, Pod pod) {
        updateMorning(request, pod);
        updateAfternoon(request, pod);
        return podAssignment;
    }

    private void updateMorning(AssignmentsRequest request, Pod pod) {
        if (!podAssignment.getDay().equals(request.startDate())
                || isNull(request.startTimeSlot())
                || request.startTimeSlot().isMorning()) {
            podAssignment.assignMorningToPod(pod);
        }
    }

    private void updateAfternoon(AssignmentsRequest request, Pod pod) {
        if (!podAssignment.getDay().equals(request.endDate())
                || isNull(request.endTimeSlot())
                || request.endTimeSlot().isAfternoon()) {
            podAssignment.assignAfternoonToPod(pod);
        }
    }
}
