package com.ubs.cpt.testdata.user;

import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.domain.entity.user.UserBookedCapacity;
import com.ubs.cpt.infra.test.base.TestDataSuite;
import com.ubs.cpt.testdata.pod.PodTestdata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Testdata for {@link UserBookedCapacity}.
 *
 * @author Amar Pandav
 */
public class UserBookedCapacityTestdata {

   private LocalDate day;

    private User user;

    private Pod morningPod;

    private Pod afternoonPod;

    public UserBookedCapacityTestdata withDay(LocalDate day) {
        this.day = day;
        return this;
    }

    public UserBookedCapacityTestdata withUser(User user) {
        this.user = user;
        return this;
    }

    public UserBookedCapacityTestdata withMorningPod(Pod morningPod) {
        this.morningPod = morningPod;
        return this;
    }

    public UserBookedCapacityTestdata withAfternoonPod(Pod afternoonPod) {
        this.afternoonPod = afternoonPod;
        return this;
    }



    public UserBookedCapacity create() {
        UserBookedCapacity userBookedCapacity = new UserBookedCapacity(day, user, morningPod, afternoonPod);
        return userBookedCapacity;
    }


    public static class SuiteSynthetic extends TestDataSuite<UserBookedCapacity> {
        public UserBookedCapacity AMAR_AURA_AURA_DAY1 = register(new UserBookedCapacityTestdata()
                .withDay(LocalDate.parse("01.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningPod(PodTestdata.suiteSynthetic().AURA)
                .withAfternoonPod(PodTestdata.suiteSynthetic().AURA)
                .create());
        public UserBookedCapacity AMAR_AURA_GIM_DAY2 = register(new UserBookedCapacityTestdata()
                .withDay(LocalDate.parse("02.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningPod(PodTestdata.suiteSynthetic().AURA)
                .withAfternoonPod(PodTestdata.suiteSynthetic().GIM)
                .create());
        public UserBookedCapacity AMAR_NULL_AURA_DAY2 = register(new UserBookedCapacityTestdata()
                .withDay(LocalDate.parse("03.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withAfternoonPod(PodTestdata.suiteSynthetic().AURA)
                .create());

        public UserBookedCapacity THOMAS_AURA_AURA_DAY1 = register(new UserBookedCapacityTestdata()
                .withDay(LocalDate.parse("01.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningPod(PodTestdata.suiteSynthetic().AURA)
                .withAfternoonPod(PodTestdata.suiteSynthetic().AURA)
                .create());
        public UserBookedCapacity THOMAS_GIM_AURA_DAY2 = register(new UserBookedCapacityTestdata()
                .withDay(LocalDate.parse("02.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningPod(PodTestdata.suiteSynthetic().GIM)
                .withAfternoonPod(PodTestdata.suiteSynthetic().AURA)
                .create());
        public UserBookedCapacity THOMAS_AURA_NULL_DAY2 = register(new UserBookedCapacityTestdata()
                .withDay(LocalDate.parse("06.08.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withUser(UserTestdata.suiteSynthetic().AMAR)
                .withMorningPod(PodTestdata.suiteSynthetic().AURA)
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
