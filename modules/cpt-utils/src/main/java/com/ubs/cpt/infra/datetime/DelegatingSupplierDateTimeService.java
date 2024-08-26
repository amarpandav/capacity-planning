package com.ubs.cpt.infra.datetime;

import java.time.*;
import java.util.Date;
import java.util.function.Supplier;

public class DelegatingSupplierDateTimeService implements DateTimeService {
    private final Supplier<DateTimeService> dateTimeService;

    public DelegatingSupplierDateTimeService(Supplier<DateTimeService> dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @Override
    public ZoneId timeZoneId() {
        return dateTimeService.get().timeZoneId();
    }

    @Override
    public ZoneOffset zoneOffset() {
        return dateTimeService.get().zoneOffset();
    }

    @Override
    public LocalDate todayLocalDate() {
        return dateTimeService.get().todayLocalDate();
    }

    @Override
    public LocalDateTime todayLocalDateTime() {
        return dateTimeService.get().todayLocalDateTime();
    }

    @Override
    public ZonedDateTime todayZonedDateTime() {
        return dateTimeService.get().todayZonedDateTime();
    }

    @Override
    public Date todayDate() {
        return dateTimeService.get().todayDate();
    }

    @Override
    public long currentTimeMillis() {
        return dateTimeService.get().currentTimeMillis();
    }
}
