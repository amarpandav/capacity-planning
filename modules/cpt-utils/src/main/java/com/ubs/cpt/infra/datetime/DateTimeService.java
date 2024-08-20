package com.ubs.cpt.infra.datetime;

import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;

/**
 * @author Amar Pandav
 */
@Service
public class DateTimeService {
    private static final ZoneId JAVA_TIME_ZONE_ID = ZoneId.of("Europe/Zurich");

    public ZoneId timeZoneId() {
        return JAVA_TIME_ZONE_ID;
    }

    public ZoneOffset zoneOffset() {
        Instant instant = Instant.now();
        return timeZoneId().getRules().getOffset(instant);
    }

    public LocalDate todayLocalDate() {
        return LocalDate.now(JAVA_TIME_ZONE_ID);
    }

    public LocalDateTime todayLocalDateTime() {
        return LocalDateTime.now(timeZoneId());
    }

    public ZonedDateTime todayZonedDateTime() {
        return ZonedDateTime.now(timeZoneId());
    }

    public Date todayDate() {
        return new Date(
            todayLocalDateTime().toInstant(zoneOffset()).toEpochMilli()
        );
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}