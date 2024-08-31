package com.ubs.cpt.service.dto;

public enum TimeSlot {
    MORNING,
    AFTERNOON;

    public boolean isMorning() {
        return this == MORNING;
    }

    public boolean isAfternoon() {
        return this == AFTERNOON;
    }
}
