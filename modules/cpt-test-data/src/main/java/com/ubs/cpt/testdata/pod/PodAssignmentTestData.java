package com.ubs.cpt.testdata.pod;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.test.base.TestDataSuite;
import com.ubs.cpt.testdata.user.UserTestdata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Testdata for {@link PodAssignment}.
 *
 * @author Lukasz Piotrowski
 */
public class PodAssignmentTestData {

    private LocalDate day;
    private User user;
    private AvailabilityType morningAvailability;
    private AvailabilityType afternoonAvailability;
    private Pod morningPod;
    private Pod afternoonPod;

    public PodAssignmentTestData withDay(LocalDate day) {
        this.day = day;
        return this;
    }
    public PodAssignmentTestData withUser(User user) {
        this.user = user;
        return this;
    }

    public PodAssignmentTestData withMorningAvailabilityType(AvailabilityType availabilityType) {
        this.morningAvailability = availabilityType;
        return this;
    }

    public PodAssignmentTestData withAfternoonAvailabilityType(AvailabilityType availabilityType) {
        this.afternoonAvailability = availabilityType;
        return this;
    }

    public PodAssignmentTestData withMorningPod(Pod pod) {
        this.morningPod = pod;
        return this;
    }

    public PodAssignmentTestData withAfternoonPod(Pod pod) {
        this.afternoonPod = pod;
        return this;
    }

    public PodAssignment create() {
        return new PodAssignment(day, user, morningAvailability, afternoonAvailability, morningPod, afternoonPod);
    }

    public static class SuiteSynthetic extends TestDataSuite<PodAssignment> {
        public PodAssignment POD_ASSIGNMENT_1 = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 8, 21))
                .withUser(UserTestdata.suiteSynthetic().TIM)
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.AVAILABLE)
                .withMorningPod(PodTestdata.suiteSynthetic().AURA)
                .create());

        public PodAssignment POD_ASSIGNMENT_2 = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 8, 23))
                .withUser(UserTestdata.suiteSynthetic().TIM)
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment POD_ASSIGNMENT_3 = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 8, 24))
                .withUser(UserTestdata.suiteSynthetic().TIM)
                .withMorningAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .withAfternoonAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .create());

        public PodAssignment POD_ASSIGNMENT_4 = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 8, 25))
                .withUser(UserTestdata.suiteSynthetic().TIM)
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().GIM)
                .withAfternoonPod(PodTestdata.suiteSynthetic().GIM)
                .create());

        public PodAssignment AMAR_ABSENT_DAY1 = register(new PodAssignmentTestData()
                .withDay(LocalDate.parse("13.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment AMAR_ABSENT_DAY2 = register(new PodAssignmentTestData()
                .withDay(LocalDate.parse("14.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .create());

        public PodAssignment THOMAS_ABSENT_DAY1 = register(new PodAssignmentTestData()
                .withDay(LocalDate.parse("09.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment THOMAS_ABSENT_DAY2 = register(new PodAssignmentTestData()
                .withDay(LocalDate.parse("18.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

    }

    private static SuiteSynthetic synthetic;

    public static SuiteSynthetic suiteSynthetic() {
        if (synthetic == null) {
            synthetic = new SuiteSynthetic();
        }
        return synthetic;
    }
}
