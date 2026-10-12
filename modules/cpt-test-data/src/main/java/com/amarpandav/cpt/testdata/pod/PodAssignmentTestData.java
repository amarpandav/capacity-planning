package com.amarpandav.cpt.testdata.pod;

import com.amarpandav.cpt.domain.entity.availability.AvailabilityType;
import com.amarpandav.cpt.domain.entity.pod.Pod;
import com.amarpandav.cpt.domain.entity.pod.PodAssignment;
import com.amarpandav.cpt.domain.entity.user.User;
import com.amarpandav.cpt.infra.test.base.TestDataSuite;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Seeded days are relative to the first Monday of the current month, so they always fall into the scheduler's
     * default view (current month and the next two months).
     */
    private static final LocalDate FIRST_MONDAY_OF_MONTH = LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

    private static LocalDate day(int week, DayOfWeek dayOfWeek) {
        return FIRST_MONDAY_OF_MONTH.plusWeeks(week).with(dayOfWeek);
    }

    public static class SuiteSynthetic extends TestDataSuite<PodAssignment> {
        public PodAssignment AMAR_ASSIGNMENT_1_ATLAS_ATLAS = register(new PodAssignmentTestData()
                .withDay(day(0, DayOfWeek.FRIDAY))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.AVAILABLE)
                .withMorningPod(PodTestdata.suiteSynthetic().ATLAS)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_2_ATLAS_ORION = register(new PodAssignmentTestData()
                .withDay(day(0, DayOfWeek.MONDAY))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().ATLAS)
                .withAfternoonPod(PodTestdata.suiteSynthetic().ORION)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_3_ABSENT_ABSENT = register(new PodAssignmentTestData()
                .withDay(day(1, DayOfWeek.TUESDAY))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_4_PUBLIC_HOLIDAY_ABSENT = register(new PodAssignmentTestData()
                .withDay(day(1, DayOfWeek.WEDNESDAY))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment AMAR_ASSIGNMENT_5_PUBLIC_HOLIDAY_PUBLIC_HOLIDAY = register(new PodAssignmentTestData()
                .withDay(day(1, DayOfWeek.WEDNESDAY))
                .withUser(PodMemberTestdata.suiteSynthetic().AMAR_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .withAfternoonAvailabilityType(AvailabilityType.PUBLIC_HOLIDAY)
                .create());

        public PodAssignment LIAM_ASSIGNMENT_1_ATLAS_ATLAS = register(new PodAssignmentTestData()
                .withDay(day(3, DayOfWeek.WEDNESDAY))
                .withUser(PodMemberTestdata.suiteSynthetic().LIAM_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().ATLAS)
                .withAfternoonPod(PodTestdata.suiteSynthetic().ATLAS)
                .create());

        public PodAssignment LIAM_ASSIGNMENT_2_ORION_ABSENT = register(new PodAssignmentTestData()
                .withDay(day(3, DayOfWeek.FRIDAY))
                .withUser(PodMemberTestdata.suiteSynthetic().LIAM_FOR_ORION.getUser())
                .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                .withMorningPod(PodTestdata.suiteSynthetic().ORION)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        public PodAssignment TOBIAS_ASSIGNMENT_1_ABSENT_ABSENT = register(new PodAssignmentTestData()
                .withDay(day(2, DayOfWeek.WEDNESDAY))
                .withUser(PodMemberTestdata.suiteSynthetic().TOBIAS_FOR_ATLAS.getUser())
                .withMorningAvailabilityType(AvailabilityType.ABSENT)
                .withAfternoonAvailabilityType(AvailabilityType.ABSENT)
                .create());

        /**
         * Liam works for both pods in week 5: ATLAS in the mornings, ORION in the afternoons.
         */
        public List<PodAssignment> LIAM_WEEK_ATLAS_MORNINGS_ORION_AFTERNOONS = registerWorkWeek(5,
                PodMemberTestdata.suiteSynthetic().LIAM_FOR_ATLAS.getUser(),
                PodTestdata.suiteSynthetic().ATLAS, PodTestdata.suiteSynthetic().ORION);

        private List<PodAssignment> registerWorkWeek(int week, User user, Pod morningPod, Pod afternoonPod) {
            List<PodAssignment> podAssignments = new ArrayList<>();
            for (DayOfWeek dayOfWeek = DayOfWeek.MONDAY; dayOfWeek != DayOfWeek.SATURDAY; dayOfWeek = dayOfWeek.plus(1)) {
                podAssignments.add(register(new PodAssignmentTestData()
                        .withDay(day(week, dayOfWeek))
                        .withUser(user)
                        .withMorningAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                        .withAfternoonAvailabilityType(AvailabilityType.POD_ASSIGNMENT)
                        .withMorningPod(morningPod)
                        .withAfternoonPod(afternoonPod)
                        .create()));
            }
            return podAssignments;
        }
    }

    private static SuiteSynthetic synthetic;

    public static SuiteSynthetic suiteSynthetic() {
        if (synthetic == null) {
            synthetic = new SuiteSynthetic();
        }
        return synthetic;
    }
}
