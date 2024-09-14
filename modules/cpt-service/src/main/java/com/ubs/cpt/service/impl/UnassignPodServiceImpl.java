package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.infra.exception.PodNotFoundException;
import com.ubs.cpt.service.UnassignPodService;
import com.ubs.cpt.service.dto.AssignmentsRequest;
import com.ubs.cpt.service.repository.PodAssignmentRepository;
import com.ubs.cpt.service.repository.PodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class UnassignPodServiceImpl implements UnassignPodService {

    private final PodAssignmentRepository podAssignmentRepository;
    private final PodRepository podRepository;

    public UnassignPodServiceImpl(PodAssignmentRepository podAssignmentRepository,
                                  PodRepository podRepository) {
        this.podAssignmentRepository = podAssignmentRepository;
        this.podRepository = podRepository;
    }

    @Override
    public void execute(AssignmentsRequest request) {
        log.info("delete pod assignments for request: {}", request);
        validate(request);
        String podId = request.podId();
        Pod pod = podRepository.findById(new EntityId<>(podId))
                .orElseThrow(() -> new PodNotFoundException("pod not found by id " + podId));

        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();
        List<PodAssignment> existingPodAssignments = podAssignmentRepository.getPodAssignment(request.userIds(),
                podId, startDate, endDate);

        existingPodAssignments.forEach(podAssignment -> {
            LocalDate day = podAssignment.getDay();
            if (day.isEqual(startDate) && nonNull(request.startTimeSlot()) && request.startTimeSlot().isAfternoon()
                    && podAssignment.getMorningAvailabilityType().isPodAssignment()) {
                podAssignment.unassignAfternoonPod(pod);
            } else if (day.isEqual(endDate) && nonNull(request.endTimeSlot()) && request.endTimeSlot().isMorning()
                    && podAssignment.getAfternoonAvailabilityType().isPodAssignment()) {
                podAssignment.unassignMorningPod(pod);
            } else {
                podAssignment.unassignPod(pod);
            }
        });
        podAssignmentRepository.saveAll(existingPodAssignments);
    }

    private void validate(AssignmentsRequest request) {
        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();

        Period duration = startDate.until(endDate);
        if (duration.toTotalMonths() > 12) {
            throw new IllegalArgumentException("duration longer than 12 months between startDate and endDate is not supported");
        }
    }
}