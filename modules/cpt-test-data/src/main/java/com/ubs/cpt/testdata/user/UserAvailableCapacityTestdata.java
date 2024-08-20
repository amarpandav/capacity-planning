package com.ubs.cpt.testdata.user;

import com.ubs.cpt.domain.entity.availability.Availability;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.domain.entity.user.UserAvailableCapacity;
import com.ubs.cpt.infra.test.base.TestDataSuite;
import com.ubs.cpt.testdata.availability.AvailabilityTestdata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Testdata for {@link com.ubs.cpt.domain.entity.user.UserAvailableCapacity}.
 *
 * @author Amar Pandav
 */
public class UserAvailableCapacityTestdata {

   private LocalDate day;

    private User user;

    private Availability morningAvailability;

    private Availability afternoonAvailability;

    public UserAvailableCapacityTestdata withDay(LocalDate day) {
        this.day = day;
        return this;
    }

    public UserAvailableCapacityTestdata withUser(User user) {
        this.user = user;
        return this;
    }

    public UserAvailableCapacityTestdata withMorningAvailability(Availability morningAvailability) {
        this.morningAvailability = morningAvailability;
        return this;
    }

    public UserAvailableCapacityTestdata withAfternoonAvailability(Availability afternoonAvailability) {
        this.afternoonAvailability = afternoonAvailability;
        return this;
    }



    public UserAvailableCapacity create() {
        UserAvailableCapacity userAvailableCapacity = new UserAvailableCapacity(day, user, morningAvailability, afternoonAvailability);
        return userAvailableCapacity;
    }


    public static class SuiteSynthetic extends TestDataSuite<UserAvailableCapacity> {
        public UserAvailableCapacity AMAR_ABSENT_DAY1 = register(new UserAvailableCapacityTestdata()
                .withDay(LocalDate.parse("13.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .withAfternoonAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .create());

        public UserAvailableCapacity AMAR_ABSENT_DAY2 = register(new UserAvailableCapacityTestdata()
                .withDay(LocalDate.parse("14.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .withAfternoonAvailability(AvailabilityTestdata.suiteSynthetic().PUBLIC_HOLIDAY)
                .create());

        public UserAvailableCapacity THOMAS_ABSENT_DAY1 = register(new UserAvailableCapacityTestdata()
                .withDay(LocalDate.parse("09.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withAfternoonAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .create());

        public UserAvailableCapacity THOMAS_ABSENT_DAY2 = register(new UserAvailableCapacityTestdata()
                .withDay(LocalDate.parse("18.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .withAfternoonAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
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
