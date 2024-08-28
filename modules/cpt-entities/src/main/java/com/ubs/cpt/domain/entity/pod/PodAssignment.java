package com.ubs.cpt.domain.entity.pod;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.domain.base.FieldConstants;
import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

//TODO: Amar feedback: Use PodAssignment instead of UserBookedCapacity and UserUnAvailableCapacity
/**
 * Entity representing cpt_pod_assignment DB table.
 */
@Entity
@Table(
        name = PodAssignment.TABLE_NAME
)
//@org.hibernate.annotations.Check(constraints = "(morning_pod_uuid IS NOT NULL OR afternoon_pod_uuid IS NOT NULL)")
//TODO: Amar feedback: We need new constraint, i haven't tested below constraint hence commented out
//@org.hibernate.annotations.Check(constraints ="(morning_availability_type = 'POD_ASSIGNMENT' and morning_pod_uuid IS NOT NULL) OR (afternoon_availability_type = 'POD_ASSIGNMENT' and afternoon_availability_type IS NOT NULL)")
public class PodAssignment extends BaseEntity<PodAssignment> {
    public static final String TABLE_NAME = "cpt_pod_assignment";

    public static final class Columns {
        public static final String DAY = "day";
        public static final String USER_UUID = "user_uuid";
        public static final String MORNING_AVAILABILITY_TYPE = "morning_availability_type";
        public static final String AFTERNOON_AVAILABILITY_TYPE = "afternoon_availability_type";
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

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(name = Columns.MORNING_AVAILABILITY_TYPE, length = FieldConstants.ENUM_30, nullable = false)
    private AvailabilityType morningAvailabilityType;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(name = Columns.AFTERNOON_AVAILABILITY_TYPE, length = FieldConstants.ENUM_30, nullable = false)
    private AvailabilityType afternoonAvailabilityType;

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

    protected PodAssignment() {// required by JPA
    }

    public PodAssignment(LocalDate day,
                         User user,
                         AvailabilityType morningAvailabilityType,
                         AvailabilityType afternoonAvailabilityType,

                         Pod morningPod,
                         Pod afternoonPod) {
        this.day = day;
        this.user = user;
        this.morningAvailabilityType = morningAvailabilityType;
        this.afternoonAvailabilityType = afternoonAvailabilityType;
        this.morningPod = morningPod;
        this.afternoonPod = afternoonPod;
        if (morningAvailabilityType.isPodAssignment() && morningPod == null) {
            throw new IllegalStateException("You are trying to assign the user to morning pod without morning_pod_uuid.");
        }
        if (afternoonAvailabilityType.isPodAssignment() && afternoonPod == null) {
            throw new IllegalStateException("You are trying to assign the user to afternoon pod without afternoon_pod_uuid.");
        }

        if ( (morningAvailabilityType.isAbsent() ||  morningAvailabilityType.isPublicHoliday()) && morningPod != null) {
            throw new IllegalStateException("You are trying to assign the user to morning pod with morningAvailabilityType: "+afternoonAvailabilityType.name() + " which is not allowed!");
        }
        if ( (afternoonAvailabilityType.isAbsent() ||  afternoonAvailabilityType.isPublicHoliday()) && afternoonPod != null) {
            throw new IllegalStateException("You are trying to assign the user to afternoon pod with afternoonAvailabilityType: "+afternoonAvailabilityType.name() + " which is not allowed!");
        }
    }

    public LocalDate getDay() {
        return day;
    }

    public User getUser() {
        return user;
    }

    public AvailabilityType getMorningAvailabilityType() {
        return morningAvailabilityType;
    }

    public AvailabilityType getAfternoonAvailabilityType() {
        return afternoonAvailabilityType;
    }

    public Pod getMorningPod() {
        return morningPod;
    }

    public Pod getAfternoonPod() {
        return afternoonPod;
    }
}

