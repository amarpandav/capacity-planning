package com.ubs.cpt.core.project;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Optional;

@ToString
@EqualsAndHashCode
public class ProjectDates {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public ProjectDates(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Optional<LocalDate> startDate() {
        return Optional.ofNullable(startDate);
    }

    public Optional<LocalDate> endDate() {
        return Optional.ofNullable(endDate);
    }
}
