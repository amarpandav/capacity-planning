package com.ubs.cpt.infra.datetime;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;

/**
 * @author Amar Pandav
 */
@Service
@Profile("!test")
public class ZurichDateTimeService implements DateTimeService {
    private static final ZoneId JAVA_TIME_ZONE_ID = ZoneId.of("Europe/Zurich");

    @Override
    public ZoneId timeZoneId() {
        return JAVA_TIME_ZONE_ID;
    }

    @Override
    public ZoneOffset zoneOffset() {
        Instant instant = Instant.now();
        return timeZoneId().getRules().getOffset(instant);
    }

    @Override
    public LocalDate todayLocalDate() {
        return LocalDate.now(JAVA_TIME_ZONE_ID);
    }

    @Override
    public LocalDateTime todayLocalDateTime() {
        return LocalDateTime.now(timeZoneId());
    }

    @Override
    public ZonedDateTime todayZonedDateTime() {
        return ZonedDateTime.now(timeZoneId());
    }

    @Override
    public Date todayDate() {
        return new Date(
            todayLocalDateTime().toInstant(zoneOffset()).toEpochMilli()
        );
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}