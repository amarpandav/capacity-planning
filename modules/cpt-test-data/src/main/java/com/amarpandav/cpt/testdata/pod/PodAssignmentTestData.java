package com.amarpandav.cpt.testdata.pod;

import com.amarpandav.cpt.domain.entity.availability.AvailabilityType;
import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.domain.entity.pod.PodAssignment;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.infra.test.base.TestDataSuite;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Testdata for {@link PodAssignment}.
 *
 * @author Liam Parker
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
        public PodAssignment AMAR_ASSIGNMENT_1_ATLAS_ATLAS = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 1))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.AVAILABLE)
                .withMorningPod(PodTestdata.suiteSynthetic().ATLAS)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_2_ATLAS_ORION = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 2))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().ATLAS)
                .withAfternoonPod(PodTestdata.suiteSynthetic().ORION)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_3_ABSENT_ABSENT = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 10))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_4_PUBLIC_HOLIDAY_ABSENT = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 11))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_5_PUBLIC_HOLIDAY_PUBLIC_HOLIDAY = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 11))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .withAfternoonAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .create());

        public PodAssignment LIAM_ASSIGNMENT_1_ATLAS_ATLAS = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 25))
                .withUser(PodMemberTestdata.suiteSynthetic().LIAM_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().ATLAS)
                .withAfternoonPod(PodTestdata.suiteSynthetic().ATLAS)
                .create());

        public PodAssignment LIAM_ASSIGNMENT_2_ORION_ABSENT = register(new PodAssignmentTestData()
                .withDay(LocalDate.of(2024, 9, 27))
                .withUser(PodMemberTestdata.suiteSynthetic().LIAM_FOR_ORION.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().ORION)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment TOBIAS_ASSIGNMENT_1_ABSENT_ABSENT = register(new PodAssignmentTestData()
                .withDay(LocalDate.parse("18.09.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(PodMemberTestdata.suiteSynthetic().TOBIAS_FOR_ATLAS.getUser())
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
