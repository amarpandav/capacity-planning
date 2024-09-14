package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.service.dto.AssignmentsRequest;
import com.ubs.cpt.service.dto.TimeSlot;
import com.ubs.cpt.testdata.pod.PodTestdata;
import com.ubs.cpt.testdata.user.UserTestdata;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PodAssignmentUpdaterTest {

    private final Pod aura = PodTestdata.suiteSynthetic().AURA;
    private final PodAssignment podAssignment = new PodAssignment(LocalDate.of(2024, 8, 1), UserTestdata.suiteSynthetic().LUKASZ);

    @Test
    void shouldAssignOnlyMorning() {
        AssignmentsRequest request = new AssignmentsRequest(
                aura.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay(), podAssignment.getDay(), TimeSlot.MORNING, TimeSlot.MORNING);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, aura);

        assertTrue(updated.getMorningAvailabilityType().isPodAssignment());
        assertNotNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isAvailable());
        assertNull(updated.getAfternoonPod());
    }

    @Test
    void shouldAssignOnlyAfternoon() {
        AssignmentsRequest request = new AssignmentsRequest(
                aura.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay(), podAssignment.getDay(), TimeSlot.AFTERNOON, TimeSlot.AFTERNOON);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, aura);

        assertTrue(updated.getMorningAvailabilityType().isAvailable());
        assertNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(updated.getAfternoonPod());
    }

    @Test
    void whenTimeSlotMissing_thenAssignFullDay() {
        AssignmentsRequest request = new AssignmentsRequest(
                aura.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay().minusDays(3), podAssignment.getDay().plusDays(3), null, null);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, aura);

        assertTrue(updated.getMorningAvailabilityType().isPodAssignment());
        assertNotNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(updated.getAfternoonPod());
    }

    @Test
    void whenDayBetweenStartAndEnd_thenAssignFullDay() {
        AssignmentsRequest request = new AssignmentsRequest(
                aura.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay().minusDays(3), podAssignment.getDay().plusDays(3), TimeSlot.MORNING, TimeSlot.MORNING);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, aura);

        assertTrue(updated.getMorningAvailabilityType().isPodAssignment());
        assertNotNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(updated.getAfternoonPod());
    }
}