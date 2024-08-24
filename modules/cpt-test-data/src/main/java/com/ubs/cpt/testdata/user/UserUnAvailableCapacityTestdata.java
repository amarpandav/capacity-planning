package com.ubs.cpt.testdata.user;

import com.ubs.cpt.domain.entity.availability.Availability;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.domain.entity.user.UserUnAvailableCapacity;
import com.ubs.cpt.infra.test.base.TestDataSuite;
import com.ubs.cpt.testdata.availability.AvailabilityTestdata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Testdata for {@link UserUnAvailableCapacity}.
 *
 * @author Amar Pandav
 */
public class UserUnAvailableCapacityTestdata {

   private LocalDate day;

    private User user;

    private Availability morningAvailability;

    private Availability afternoonAvailability;

    public UserUnAvailableCapacityTestdata withDay(LocalDate day) {
        this.day = day;
        return this;
    }

    public UserUnAvailableCapacityTestdata withUser(User user) {
        this.user = user;
        return this;
    }

    public UserUnAvailableCapacityTestdata withMorningAvailability(Availability morningAvailability) {
        this.morningAvailability = morningAvailability;
        return this;
    }

    public UserUnAvailableCapacityTestdata withAfternoonAvailability(Availability afternoonAvailability) {
        this.afternoonAvailability = afternoonAvailability;
        return this;
    }



    public UserUnAvailableCapacity create() {
        UserUnAvailableCapacity userUnAvailableCapacity = new UserUnAvailableCapacity(day, user, morningAvailability, afternoonAvailability);
        return userUnAvailableCapacity;
    }


    public static class SuiteSynthetic extends TestDataSuite<UserUnAvailableCapacity> {
        public UserUnAvailableCapacity AMAR_ABSENT_DAY1 = register(new UserUnAvailableCapacityTestdata()
                .withDay(LocalDate.parse("13.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .withAfternoonAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .create());

        public UserUnAvailableCapacity AMAR_ABSENT_DAY2 = register(new UserUnAvailableCapacityTestdata()
                .withDay(LocalDate.parse("14.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .withAfternoonAvailability(AvailabilityTestdata.suiteSynthetic().PUBLIC_HOLIDAY)
                .create());

        public UserUnAvailableCapacity THOMAS_ABSENT_DAY1 = register(new UserUnAvailableCapacityTestdata()
                .withDay(LocalDate.parse("09.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withAfternoonAvailability(AvailabilityTestdata.suiteSynthetic().ABSENT)
                .create());

        public UserUnAvailableCapacity THOMAS_ABSENT_DAY2 = register(new UserUnAvailableCapacityTestdata()
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
