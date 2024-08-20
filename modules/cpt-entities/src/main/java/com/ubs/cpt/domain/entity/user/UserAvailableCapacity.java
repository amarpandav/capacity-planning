package com.ubs.cpt.domain.entity.user;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.domain.entity.availability.Availability;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

/**
 * Entity representing cpt_user_available_capacity DB table.
 */
@Entity
@Table(
        name = UserAvailableCapacity.TABLE_NAME
)
@org.hibernate.annotations.Check(constraints = "(morning_availability_uuid IS NOT NULL OR afternoon_availability_uuid IS NOT NULL)")
public class UserAvailableCapacity extends BaseEntity<UserAvailableCapacity> {
    public static final String TABLE_NAME = "cpt_user_available_capacity";

    public static final class Columns {
        public static final String DAY = "day";
        public static final String USER_UUID = "user_uuid";
        public static final String MORNING_AVAILABILITY_UUID = "morning_availability_uuid";
        public static final String AFTERNOON_AVAILABILITY_UUID = "afternoon_availability_uuid";
    }

    /**
     * User is booked for this day
     */
    @Column(name = Columns.DAY, nullable = false)
    private LocalDate day;


    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when User is deleted, this row must also be deleted
    @ManyToOne
    @JoinColumn(name = Columns.USER_UUID)
    private User user;

    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when Availability is deleted, this row must also be deleted
    @ManyToOne
    @JoinColumn(name = Columns.MORNING_AVAILABILITY_UUID)
    private Availability morningAvailability;

    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when Availability is deleted,  this row must also be deleted
    @ManyToOne
    @JoinColumn(name = Columns.AFTERNOON_AVAILABILITY_UUID)
    private Availability afternoonAvailability;

    protected UserAvailableCapacity() {// required by JPA
    }

    public UserAvailableCapacity(LocalDate day, User user, Availability morningAvailability, Availability afternoonAvailability) {
        this.day = day;
        this.user = user;
        this.morningAvailability = morningAvailability;
        this.afternoonAvailability = afternoonAvailability;
        if (morningAvailability == null && afternoonAvailability == null) {
            throw new IllegalStateException("morningAvailability or afternoonAvailability is required to save cpt_user_available_capacity");
        }
        if (morningAvailability != null && morningAvailability.getAvailabilityType().isAvailable()) {
            throw new IllegalStateException("You are trying to save morningAvailability with type " + morningAvailability.getAvailabilityType().name() + ", which is not allowed!");
        }
        if (afternoonAvailability != null && afternoonAvailability.getAvailabilityType().isAvailable()) {
            throw new IllegalStateException("You are trying to save afternoonAvailability with type " + afternoonAvailability.getAvailabilityType().name() + ", which is not allowed!");
        }
    }

    public LocalDate getDay() {
        return day;
    }

    public User getUser() {
        return user;
    }

    public Availability getMorningAvailability() {
        return morningAvailability;
    }

    public Availability getAfternoonAvailability() {
        return afternoonAvailability;
    }
}

