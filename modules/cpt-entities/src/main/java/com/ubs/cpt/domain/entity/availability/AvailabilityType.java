package com.ubs.cpt.domain.entity.availability;

public enum AvailabilityType {
    AVAILABLE,
    ABSENT,
    PUBLIC_HOLIDAY,
    POD_ASSIGNMENT;

    public boolean isAvailable() {
        return this == AvailabilityType.AVAILABLE;
    }

    public boolean isNotAvailable() {
        return !isAvailable();
    }

    public boolean isAbsent() {
        return this == AvailabilityType.ABSENT;
    }

    public boolean isPublicHoliday() {
        return this == AvailabilityType.PUBLIC_HOLIDAY;
    }

    public boolean isPodAssignment() {
        return this == AvailabilityType.POD_ASSIGNMENT;
    }
}

