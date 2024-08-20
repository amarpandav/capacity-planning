package com.ubs.cpt.domain.entity.user;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.domain.entity.pod.Pod;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

/**
 * Entity representing cpt_user_booked_capacity DB table.
 */
@Entity
@Table(
        name = UserBookedCapacity.TABLE_NAME
)
@org.hibernate.annotations.Check(constraints = "(morning_pod_uuid IS NOT NULL OR afternoon_pod_uuid IS NOT NULL)")
public class UserBookedCapacity extends BaseEntity<UserBookedCapacity> {
    public static final String TABLE_NAME = "cpt_user_booked_capacity";

    public static final class Columns {
        public static final String DAY = "day";
        public static final String USER_UUID = "user_uuid";
        public static final String MORNING_POD_UUID = "morning_pod_uuid";
        public static final String AFTERNOON_POD_UUID = "afternoon_pod_uuid";
    }

    /**
     * User is booked for this day
     */
    @Column(name = Columns.DAY, nullable = false)
    private LocalDate day;


    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when User is deleted, bookings must also be deleted
    @ManyToOne
    @JoinColumn(name = Columns.USER_UUID)
    private User user;

    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when Pod is deleted, bookings must also be deleted
    @ManyToOne
    @JoinColumn(name = Columns.MORNING_POD_UUID)
    private Pod morningPod;

    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when Pod is deleted, bookings must also be deleted
    @ManyToOne
    @JoinColumn(name = Columns.AFTERNOON_POD_UUID)
    private Pod afternoonPod;

    protected UserBookedCapacity() {// required by JPA
    }

    public UserBookedCapacity(LocalDate day, User user, Pod morningPod, Pod afternoonPod) {
        this.day = day;
        this.user = user;
        this.morningPod = morningPod;
        this.afternoonPod = afternoonPod;
        if (morningPod == null && afternoonPod == null) {
            throw new IllegalStateException("morningPod or afternoonPod is required to book a user");
        }
    }

    public LocalDate getDay() {
        return day;
    }

    public User getUser() {
        return user;
    }

    public Pod getMorningPod() {
        return morningPod;
    }

    public Pod getAfternoonPod() {
        return afternoonPod;
    }
}

