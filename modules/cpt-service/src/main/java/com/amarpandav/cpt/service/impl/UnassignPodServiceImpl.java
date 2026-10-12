package com.amarpandav.cpt.service.impl;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.domain.entity.pod.PodAssignment;
import com.amarpandav.cpt.domain.entity.user.UserKey;
import com.amarpandav.cpt.infra.exception.PodNotFoundException;
import com.amarpandav.cpt.service.UnassignPodService;
import com.amarpandav.cpt.service.dto.AssignmentsRequest;
import com.amarpandav.cpt.service.dto.SkippedPodDto;
import com.amarpandav.cpt.service.dto.UnassignPodResult;
import com.amarpandav.cpt.service.repository.PodAssignmentRepository;
import com.amarpandav.cpt.service.repository.PodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * Clears the given pod from the users' half-day slots in the range. Slots of other pods are left untouched and
     * reported back, with their pod leads, so the user knows whom to talk to.
     */
    @Transactional
    @Override
    public UnassignPodResult execute(AssignmentsRequest request, UserKey actingUser) {
        log.info("delete pod assignments for request: {}", request);
        validate(request);
        String podId = request.podId();
        Pod pod = podRepository.findById(new EntityId<>(podId))
                .orElseThrow(() -> new PodNotFoundException("pod not found by id " + podId));
        PodLeadCheck.requirePodLead(pod, actingUser);

        List<PodAssignment> existingPodAssignments = podAssignmentRepository.getPodAssignment(request.userIds(),
                request.startDate(), request.endDate());

        List<PodAssignment> podAssignmentsOfPod = new ArrayList<>();
        Set<Pod> skippedPods = new LinkedHashSet<>();
        existingPodAssignments.forEach(podAssignment -> {
            if (pod.equals(podAssignment.getMorningPod()) || pod.equals(podAssignment.getAfternoonPod())) {
                podAssignmentsOfPod.add(podAssignment);
            }
            if (isMorningInRange(request, podAssignment.getDay())) {
                addIfOtherPod(podAssignment.getMorningPod(), pod, skippedPods);
                podAssignment.unassignMorningPod(pod);
            }
            if (isAfternoonInRange(request, podAssignment.getDay())) {
                addIfOtherPod(podAssignment.getAfternoonPod(), pod, skippedPods);
                podAssignment.unassignAfternoonPod(pod);
            }
        });
        podAssignmentRepository.saveAll(podAssignmentsOfPod);

        return new UnassignPodResult(skippedPods.stream()
                .sorted(Comparator.comparing(Pod::getPodShortName))
                .map(UnassignPodServiceImpl::toSkippedPodDto)
                .toList());
    }

    private static void addIfOtherPod(Pod slotPod, Pod podToUnassign, Set<Pod> skippedPods) {
        if (nonNull(slotPod) && !slotPod.equals(podToUnassign)) {
            skippedPods.add(slotPod);
        }
    }

    /**
     * The range starts on startDate's afternoon when startTimeSlot is AFTERNOON, so that morning is excluded.
     */
    private static boolean isMorningInRange(AssignmentsRequest request, LocalDate day) {
        return !(day.isEqual(request.startDate()) && nonNull(request.startTimeSlot()) && request.startTimeSlot().isAfternoon());
    }

    /**
     * The range ends on endDate's morning when endTimeSlot is MORNING, so that afternoon is excluded.
     */
    private static boolean isAfternoonInRange(AssignmentsRequest request, LocalDate day) {
        return !(day.isEqual(request.endDate()) && nonNull(request.endTimeSlot()) && request.endTimeSlot().isMorning());
    }

    private static SkippedPodDto toSkippedPodDto(Pod pod) {
        return new SkippedPodDto(pod.getEntityId().getUuid(), pod.getPodName(), pod.getPodShortName(),
                PodLeadCheck.podLeadNames(pod));
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
