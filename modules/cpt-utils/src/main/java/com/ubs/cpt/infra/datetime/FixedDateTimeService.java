package com.ubs.cpt.infra.datetime;

import java.time.*;
import java.util.Date;

public final class FixedDateTimeService implements DateTimeService {
    private final OffsetDateTime now;

    private FixedDateTimeService(OffsetDateTime now) {
        this.now = now;
    }

    public static FixedDateTimeService ofOffsetDateTime(OffsetDateTime now) {
        return new FixedDateTimeService(now);
    }
    public static FixedDateTimeService ofLocalDateTime(LocalDateTime now) {
        return new FixedDateTimeService(now.atOffset(ZoneOffset.ofHours(0)));
    }

    @Override
    public ZoneId timeZoneId() {
        return now.toZonedDateTime().getZone();
    }

    @Override
    public ZoneOffset zoneOffset() {
        return now.getOffset();
    }

    @Override
    public LocalDate todayLocalDate() {
        return now.toLocalDate();
    }

    @Override
    public LocalDateTime todayLocalDateTime() {
        return now.toLocalDateTime();
    }

    @Override
    public ZonedDateTime todayZonedDateTime() {
        return now.toZonedDateTime();
    }

    @Override
    public Date todayDate() {
       return Date.from(now.toInstant());
    }

    @Override
    public long currentTimeMillis() {
        return now.toInstant().toEpochMilli();
    }
}
