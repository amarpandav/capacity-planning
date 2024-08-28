package com.ubs.cpt.domain.entity.availability;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.domain.base.FieldConstants;
import jakarta.persistence.*;

/**
 * Entity representing cpt_availability table.
 * This is kind of static table with 3 rows, one row for each AvailabilityType.
 *
 * @author Amar Pandav
 */
@Entity
@Table(
        name = com.ubs.cpt.domain.entity.availability.Availability.TABLE_NAME
)
public class Availability extends BaseEntity<com.ubs.cpt.domain.entity.availability.Availability> {
    public static final String TABLE_NAME = "cpt_availability";

    public static final class Columns {
        public static final String AVAILABILITY_TYPE = "availability_type";
        public static final String AVAILABILITY_DESCRIPTION = "availability_description";
        public static final String AVAILABILITY_TYPE_STYLE_CLASS = "availability_type_style_class";
    }

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(name = Columns.AVAILABILITY_TYPE, length = FieldConstants.ENUM_30, nullable = false)
    private AvailabilityType availabilityType;


    @Column(name = Columns.AVAILABILITY_DESCRIPTION, length = FieldConstants.DESCRIPTION_SHORT, nullable = false)
    private String availabilityDescription;

    @Column(name = Columns.AVAILABILITY_TYPE_STYLE_CLASS, length = FieldConstants.GENERAL_50, nullable = false)
    private String availabilityTypeStyleClass;



    protected Availability() {// required by JPA
    }

    public Availability(AvailabilityType availabilityType, String availabilityDescription, String availabilityTypeStyleClass) {
        this.availabilityType = availabilityType;
        this.availabilityDescription = availabilityDescription;
        this.availabilityTypeStyleClass = availabilityTypeStyleClass;
    }

    public AvailabilityType getAvailabilityType() {
        return availabilityType;
    }

    public String getAvailabilityDescription() {
        return availabilityDescription;
    }

    public String getAvailabilityTypeStyleClass() {
        return availabilityTypeStyleClass;
    }
}
