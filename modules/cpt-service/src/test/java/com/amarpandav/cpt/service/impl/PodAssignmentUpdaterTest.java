package com.amarpandav.cpt.service.impl;

import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.domain.entity.pod.PodAssignment;
import com.amarpandav.cpt.service.dto.AssignmentsRequest;
import com.amarpandav.cpt.service.dto.TimeSlot;
import com.amarpandav.cpt.testdata.pod.PodTestdata;
import com.amarpandav.cpt.testdata.user.UserTestdata;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PodAssignmentUpdaterTest {

    private final Pod atlas = PodTestdata.suiteSynthetic().ATLAS;
    private final PodAssignment podAssignment = new PodAssignment(LocalDate.of(2024, 8, 1), UserTestdata.suiteSynthetic().LIAM);

    @Test
    void shouldAssignOnlyMorning() {
        AssignmentsRequest request = new AssignmentsRequest(
                atlas.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay(), podAssignment.getDay(), TimeSlot.MORNING, TimeSlot.MORNING);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, atlas);

        assertTrue(updated.getMorningAvailabilityType().isPodAssignment());
        assertNotNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isAvailable());
        assertNull(updated.getAfternoonPod());
    }

    @Test
    void shouldAssignOnlyAfternoon() {
        AssignmentsRequest request = new AssignmentsRequest(
                atlas.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay(), podAssignment.getDay(), TimeSlot.AFTERNOON, TimeSlot.AFTERNOON);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, atlas);

        assertTrue(updated.getMorningAvailabilityType().isAvailable());
        assertNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(updated.getAfternoonPod());
    }

    @Test
    void whenTimeSlotMissing_thenAssignFullDay() {
        AssignmentsRequest request = new AssignmentsRequest(
                atlas.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay().minusDays(3), podAssignment.getDay().plusDays(3), null, null);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, atlas);

        assertTrue(updated.getMorningAvailabilityType().isPodAssignment());
        assertNotNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(updated.getAfternoonPod());
    }

    @Test
    void whenDayBetweenStartAndEnd_thenAssignFullDay() {
        AssignmentsRequest request = new AssignmentsRequest(
                atlas.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay().minusDays(3), podAssignment.getDay().plusDays(3), TimeSlot.MORNING, TimeSlot.MORNING);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, atlas);

        assertTrue(updated.getMorningAvailabilityType().isPodAssignment());
        assertNotNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(updated.getAfternoonPod());
    }
}