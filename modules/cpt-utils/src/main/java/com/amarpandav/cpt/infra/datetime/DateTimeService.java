package com.amarpandav.cpt.infra.datetime;

import java.time.*;
import java.util.Date;

public interface DateTimeService {
    ZoneId timeZoneId();

    ZoneOffset zoneOffset();

    LocalDate todayLocalDate();

    LocalDateTime todayLocalDateTime();

    ZonedDateTime todayZonedDateTime();

    Date todayDate();

    long currentTimeMillis();
}
