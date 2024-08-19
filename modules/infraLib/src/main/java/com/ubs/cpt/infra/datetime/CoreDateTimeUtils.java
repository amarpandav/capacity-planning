package com.ubs.cpt.infra.datetime;

import org.joda.time.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Amar Pandav
 */
public class CoreDateTimeUtils {
    //private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CoreDateTimeUtils.class);

    public static final TimeZone JAVA_TIME_ZONE = TimeZone.getTimeZone("Europe/Zurich");
    public static final DateTimeZone JODA_DATE_TIME_ZONE = DateTimeZone.forTimeZone(JAVA_TIME_ZONE);

    /**
     * @deprecated
     */
    public static LocalDate todayLocalDateJoda() {
        return new LocalDate(new Date()); //localdate that ignores offset for timeshift
    }

    /**
     * Is the passed in date Today?
     *
     * @param dateToCompare
     * @return see above
     */
    public static boolean isToday(LocalDate dateToCompare) {
        return todayLocalDate().isEqual(dateToCompare);
    }

    public static boolean isToday(DateTime dateToCompare) {
        return todayLocalDate().isEqual(dateToCompare.toLocalDate());
    }

    /**
     * <b>Attention:</b> will return current LocalDate for instant null!!!
     *
     * @param instant local date instant
     * @return LocalDate for instant - or current LocalDate if the passed instant is null.
     */
    public static LocalDate localDateJoda(Object instant) {
        return new LocalDate(instant);
    }

    /**
     * @param instant local date instant
     * @return LocalDate for instant or null if the passed instant is null.
     */
    public static LocalDate localDateJodaOrNull(Object instant) {
        if (instant == null) {
            return null;
        }
        return new LocalDate(instant);
    }

    public static LocalDate localDateJoda(
            int year,
            int monthOfYear,
            int dayOfMonth) {
        return new LocalDate(year, monthOfYear, dayOfMonth);
    }

    public static LocalDate todayLocalDate() {
        return new LocalDate();
    }

    /**
     * @return das Kalenderdatum des heutigen Tages in der lokalen Zeitzone "Europe/Zurich"
     */
    public static LocalDate todayLocalDateWithTimeZoneJoda() {
        return new LocalDate(JODA_DATE_TIME_ZONE);
    }

    public static LocalDate localDateWithTimeZoneJoda(Object date) {
        return new LocalDate(date, JODA_DATE_TIME_ZONE); //ignores offset for timeshift
    }

    public static LocalDate localDateJoda(long instant, Chronology chronology) {
        return new LocalDate(instant, chronology);
    }


    public static LocalDateTime localDateTimeJoda(int year,
                                                  int monthOfYear,
                                                  int dayOfMonth,
                                                  int hourOfDay,
                                                  int minuteOfHour) {
        return new LocalDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour);
    }


    public static LocalDateTime localDateTimeJoda() {
        return new LocalDateTime();
    }

    public static DateTime dateTimeJoda() {
        return new DateTime();
    }

    public static DateTime dateTimeWithTimeZoneJoda() {
        return new DateTime(JODA_DATE_TIME_ZONE);
    }

    public static Date getCurrentDate() {
        return dateTimeWithTimeZoneJoda().toDate();
    }

    public static long getDateTimeMillis(int year, int monthOfYear, int dayOfMonth, int millisOfDay, Chronology chronology) {
        return chronology.getDateTimeMillis(year, monthOfYear, dayOfMonth, millisOfDay);
    }

    public static DateTime dateTimeJoda(Object instant) {
        return new DateTime(instant);
    }

    public static DateTime dateTimeJoda(long instant) {
        return new DateTime(instant);
    }

    /**
     * @param ts timestamp or null.
     * @return DateTime for passed timestamp or null if null value was passed.
     */
    public static DateTime dateTimeJoda(Timestamp ts) {
        if (ts == null) {
            return null;
        }
        long millis = ts.getTime();
        return dateTimeJoda(millis);
    }

    public static DateTime dateTimeJoda(
            int year,
            int monthOfYear,
            int dayOfMonth,
            int hourOfDay,
            int minuteOfHour,
            int secondOfMinute,
            int millisOfSecond) {
        return new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
    }

    /**
     * @return 2999-12-31
     */
    public static LocalDate getMaxValidToLocalDateJoda() {
        return new LocalDate(getEndOfTime());
    }

    public static java.util.Date todayJavaUtilDate() {
        return new java.util.Date();
    }

    @Deprecated
    public static java.util.Date javaUtilDate(int year, int month, int date) {
        return new java.util.Date(year, month, date);
    }

    public static java.util.Date javaUtilDate(long l) {
        return new java.util.Date(l);
    }

    public static long getMillis(DateTime dateTime) {
        return dateTime != null ? dateTime.getMillis() : 0L;
    }

    public static Date toDate(DateTime dateTime) {
        return dateTime != null ? dateTime.toDate() : null;
    }

    /**
     * Copied method from {@link org.joda.time.LocalDate#toDate()} to avoid problems with version 2.0 of Joda.
     * (Old Joda version is delivered with Weblogic/Jap)
     *
     * @param localDate LocalDate instance to convert
     * @return converted LocalDate instance
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        int dom = localDate.getDayOfMonth();
        Date date = CoreDateTimeUtils.javaUtilDate(localDate.getYear() - 1900, localDate.getMonthOfYear() - 1, dom);
        LocalDate check = LocalDate.fromDateFields(date);
        if (check.isBefore(localDate)) {
            // DST gap (no midnight)
            // move forward in units of one hour until date correct
            while (!check.equals(localDate)) {
                date.setTime(date.getTime() + 3600000);
                check = LocalDate.fromDateFields(date);
            }
            // move back in units of one second until date wrong
            while (date.getDate() == dom) {
                date.setTime(date.getTime() - 1000);
            }
            // fix result
            date.setTime(date.getTime() + 1000);
        } else if (check.equals(localDate)) {
            // check for DST overlap (two midnights)
            Date earlier = CoreDateTimeUtils.javaUtilDate(date.getTime() - TimeZone.getDefault().getDSTSavings());
            if (earlier.getDate() == dom) {
                date = earlier;
            }
        }
        return date;
    }


    public static Date toDateAtStartOfDay(LocalDate localDate) {
        return localDate != null ? localDate.toDateTimeAtStartOfDay(JODA_DATE_TIME_ZONE).toDate() : null;
    }

    public static DateTime toDateTimeAtStartOfDay(DateTime localDate) {
        return localDate != null ? localDate.toLocalDate().toDateTimeAtStartOfDay(JODA_DATE_TIME_ZONE) : null;
    }


    public static Calendar todayCalendar() {
        Calendar result = Calendar.getInstance();
        result.set(Calendar.HOUR, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);

        return result;
    }

    public static Calendar todayCalendarJoda() {
        return todayLocalDate().toDateTimeAtStartOfDay(JODA_DATE_TIME_ZONE).toGregorianCalendar();

    }

    public static Date getEndOfTime() {
        return gregorianCalendar(2999, 11, 31).getTime();
    }

    @SuppressWarnings("MagicConstant")
    public static java.util.Calendar gregorianCalendar(int y, int m, int d) {
        return new GregorianCalendar(y, m, d);
    }

    public static java.util.Calendar gregorianCalendar(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public static java.util.Calendar gregorianCalendarWithTimeZone(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeZone(JAVA_TIME_ZONE);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Adds the specified (signed) amount of time to the given calendar field, based on the calendar's rules to today's date.
     *
     * @param field
     * @param amount
     * @return
     */
    public static java.util.Calendar gregorianCalendarAdd(int field, int amount) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(field, amount);
        return calendar;
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long currentTimeMillisJoda() {
        return org.joda.time.DateTimeUtils.currentTimeMillis();
    }

    public static java.sql.Date javaSqlDate(LocalDate date) {
        return new java.sql.Date(date.toDateTimeAtStartOfDay().getMillis());
    }

    public static Timestamp currentJavaSqlTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

    public static Timestamp javaSqlTimestamp(long l) {
        return new Timestamp(l);
    }

    public static Timestamp toTimestamp(DateTime time) {
        return new Timestamp(time.getMillis());
    }
}