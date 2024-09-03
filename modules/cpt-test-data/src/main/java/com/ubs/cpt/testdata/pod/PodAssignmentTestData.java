package com.ubs.cpt.testdata.pod;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.test.base.TestDataSuite;

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
        public PodAssignment AMAR_ASSIGNMENT_1_AURA_AURA = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 1))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_AURA.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.AVAILABLE)
                .withMorningPod(PodTestdata.suiteSynthetic().AURA)
                .withAfternoonPod(PodTestdata.suiteSynthetic().AURA)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_2_AURA_GIM = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 1))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_AURA.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.AVAILABLE)
                .withMorningPod(PodTestdata.suiteSynthetic().AURA)
                .withAfternoonPod(PodTestdata.suiteSynthetic().GIM)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_3_ABSENT_ABSENT = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 10))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_AURA.getUser())
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_4_PUBLIC_HOLIDAY_ABSENT = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 11))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_AURA.getUser())
                .withMorningAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_5_PUBLIC_HOLIDAY_PUBLIC_HOLIDAY = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 11))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_AURA.getUser())
                .withMorningAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .withAfternoonAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .create());

        public PodAssignment LUKASZ_ASSIGNMENT_1_AURA_AURA = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 25))
                .withUser(PodMemberTestdata.suiteSynthetic().LUKASZ_FOR_AURA.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().AURA)
                .withAfternoonPod(PodTestdata.suiteSynthetic().AURA)
                .create());

        public PodAssignment LUKASZ_ASSIGNMENT_2_GIM_ABSENT = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 27))
                .withUser(PodMemberTestdata.suiteSynthetic().LUKASZ_FOR_GIM.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().GIM)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment THOMAS_ASSIGNMENT_1_ABSENT_ABSENT = register(new PodAssignmentTestData()
                .withDay(LocalDate.parse("18.09.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(PodMemberTestdata.suiteSynthetic().THOMAS_FOR_AURA.getUser())
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
