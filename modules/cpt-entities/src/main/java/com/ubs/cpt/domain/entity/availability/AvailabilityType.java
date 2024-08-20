package com.ubs.cpt.domain.entity.availability;

public enum AvailabilityType {
    AVAILABLE,
    ABSENT,
    PUBLIC_HOLIDAY;

    public boolean isAvailable() {
        return this == AvailabilityType.AVAILABLE;
    }

    public boolean isAbsent() {
        return this == AvailabilityType.ABSENT;
    }

    public boolean isPublicHoliday() {
        return this == AvailabilityType.PUBLIC_HOLIDAY;
    }
}

