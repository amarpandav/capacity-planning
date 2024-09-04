package com.ubs.cpt.service.impl;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.service.CreateAssignmentsService;
import com.ubs.cpt.service.dto.TimeSlot;
import com.ubs.cpt.testdata.pod.PodTestdata;
import com.ubs.cpt.testdata.user.UserTestdata;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PodAssignmentUpdaterTest {

    @Test
    void shouldAssignOnlyMorning() {
        Pod aura = PodTestdata.suiteSynthetic().AURA;
        PodAssignment podAssignment = new PodAssignment(LocalDate.of(2024, 8, 1), UserTestdata.suiteSynthetic().LUKASZ);
        CreateAssignmentsService.CreateAssignmentsRequest request = new CreateAssignmentsService.CreateAssignmentsRequest(
                aura.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay(), podAssignment.getDay(), TimeSlot.MORNING, TimeSlot.MORNING);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, aura);

        assertEquals(AvailabilityType.POD_ASSIGNMENT, updated.getMorningAvailabilityType());
        assertNotNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isAvailable());
        assertNull(updated.getAfternoonPod());
    }

    @Test
    void shouldAssignOnlyAfternoon() {
        Pod aura = PodTestdata.suiteSynthetic().AURA;
        PodAssignment podAssignment = new PodAssignment(LocalDate.of(2024, 8, 1), UserTestdata.suiteSynthetic().LUKASZ);
        CreateAssignmentsService.CreateAssignmentsRequest request = new CreateAssignmentsService.CreateAssignmentsRequest(
                aura.getEntityId().getUuid(), Set.of(podAssignment.getUser().getEntityId().getUuid()),
                podAssignment.getDay(), podAssignment.getDay(), TimeSlot.AFTERNOON, TimeSlot.AFTERNOON);

        PodAssignment updated = PodAssignmentUpdater.update(podAssignment)
                .with(request, aura);

        assertEquals(AvailabilityType.AVAILABLE, updated.getMorningAvailabilityType());
        assertNull(updated.getMorningPod());
        assertTrue(updated.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(updated.getAfternoonPod());
    }
}