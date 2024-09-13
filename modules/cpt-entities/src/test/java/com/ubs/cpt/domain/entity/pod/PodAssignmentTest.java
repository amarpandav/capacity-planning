package com.ubs.cpt.domain.entity.pod;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.domain.entity.user.UserKey;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PodAssignmentTest {

    private static final LocalDate WEEKEND = LocalDate.of(2024, 9, 1);
    private static final LocalDate WORK_DAY = LocalDate.of(2024, 9, 2);

    @Test
    void testAssignMorningPod() {
        PodAssignment podAssignment = getPodAssignment(WORK_DAY);
        podAssignment.assignMorningToPod(new Pod());

        assertTrue(podAssignment.getMorningAvailabilityType().isPodAssignment());
        assertNotNull(podAssignment.getMorningPod());
        assertFalse(podAssignment.getAfternoonAvailabilityType().isPodAssignment());
        assertNull(podAssignment.getAfternoonPod());
    }

    @Test
    void testAssignAfternoonPod() {
        PodAssignment podAssignment = getPodAssignment(WORK_DAY);
        podAssignment.assignAfternoonToPod(new Pod());

        assertFalse(podAssignment.getMorningAvailabilityType().isPodAssignment());
        assertNull(podAssignment.getMorningPod());
        assertTrue(podAssignment.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(podAssignment.getAfternoonPod());
    }

    @Test
    void testAssignMorningPod_duringWeekend() {
        PodAssignment podAssignment = getPodAssignment(WEEKEND);
        podAssignment.assignMorningToPod(new Pod());

        assertFalse(podAssignment.getMorningAvailabilityType().isPodAssignment());
        assertNull(podAssignment.getMorningPod());
        assertFalse(podAssignment.getAfternoonAvailabilityType().isPodAssignment());
        assertNull(podAssignment.getAfternoonPod());
    }

    @Test
    void testAssignAfternoonPod_duringWeekend() {
        PodAssignment podAssignment = getPodAssignment(WEEKEND);
        podAssignment.assignAfternoonToPod(new Pod());

        assertFalse(podAssignment.getMorningAvailabilityType().isPodAssignment());
        assertNull(podAssignment.getMorningPod());
        assertFalse(podAssignment.getAfternoonAvailabilityType().isPodAssignment());
        assertNull(podAssignment.getAfternoonPod());
    }

    @Test
    void testAssignMorningPod_notAvailable() {
        PodAssignment podAssignment = getPodAssignment(WEEKEND);
        podAssignment.setMorningAvailabilityType(AvailabilityType.ABSENT);
        podAssignment.setAfternoonAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY);

        podAssignment.assignMorningToPod(new Pod());

        assertFalse(podAssignment.getMorningAvailabilityType().isPodAssignment());
        assertNull(podAssignment.getMorningPod());
        assertFalse(podAssignment.getAfternoonAvailabilityType().isPodAssignment());
        assertNull(podAssignment.getAfternoonPod());
    }

    @Test
    void testAssignAfternoonPod_availableButPodAssigned() {
        PodAssignment podAssignment = getPodAssignment(WEEKEND);
        podAssignment.setMorningPod(new Pod());
        podAssignment.setAfternoonPod(new Pod());

        podAssignment.assignAfternoonToPod(new Pod());

        assertFalse(podAssignment.getMorningAvailabilityType().isPodAssignment());
        assertNotNull(podAssignment.getMorningPod());
        assertFalse(podAssignment.getAfternoonAvailabilityType().isPodAssignment());
        assertNotNull(podAssignment.getAfternoonPod());
    }

    @Test
    void testConstructorValidation() {
        User user = getUser();
        assertThrows(IllegalStateException.class,
                () -> new PodAssignment(WORK_DAY, user, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.AVAILABLE, null, null));
        assertThrows(IllegalStateException.class,
                () -> new PodAssignment(WORK_DAY, user, AvailabilityType.AVAILABLE, AvailabilityType.POD_ASSIGNMENT, null, null));
        assertThrows(IllegalStateException.class,
                () -> new PodAssignment(WORK_DAY, user, AvailabilityType.ABSENT, AvailabilityType.AVAILABLE, new Pod(), null));
        assertThrows(IllegalStateException.class,
                () -> new PodAssignment(WORK_DAY, user, AvailabilityType.PUBLIC_HOLIDAY, AvailabilityType.AVAILABLE, new Pod(), null));
        assertThrows(IllegalStateException.class,
                () -> new PodAssignment(WORK_DAY, user, AvailabilityType.AVAILABLE, AvailabilityType.ABSENT, null, new Pod()));
        assertThrows(IllegalStateException.class,
                () -> new PodAssignment(WORK_DAY, user, AvailabilityType.AVAILABLE, AvailabilityType.PUBLIC_HOLIDAY, null, new Pod()));
        assertThrows(IllegalStateException.class,
                () -> new PodAssignment(WORK_DAY, user, AvailabilityType.AVAILABLE, AvailabilityType.AVAILABLE, new Pod(), null));
        assertThrows(IllegalStateException.class,
                () -> new PodAssignment(WORK_DAY, user, AvailabilityType.AVAILABLE, AvailabilityType.AVAILABLE, null, new Pod()));
    }

    private PodAssignment getPodAssignment(LocalDate day) {
        return new PodAssignment(day, new User("some user", new UserKey("789456")));
    }

    private User getUser() {
        return new User("Lukasz", new UserKey("123456"));
    }
}