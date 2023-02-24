package fr.openclassrooms.mareu.utils;

import android.util.Log;

import fr.openclassrooms.mareu.BuildConfig;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

public class DateEasy {

    private static final String TAG = "DateEasy";

    private static final String sDebugZone = "Europe/Paris";
    private static final ZoneId sLocaleZone = getZoneId();
    static ZoneId getZoneId() {
        ZoneId ret = null;
        // If we are in DEBUG mode ..
        if (BuildConfig.DEBUG) {
            // .. return the debug TimeZone
            Log.d(TAG,"Debug Mode. Override TimeZone to " + sDebugZone);
            ret = ZoneId.of(sDebugZone);
        } else {
            // .. get the current TimeZone
        }
        return ret;
    }

    // full date time formatter, used by pickers
    private static final DateTimeFormatter sDateTimeFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    // full date formatter, used by pickers
    private static final DateTimeFormatter sDateFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yy");
    // special formatter for list item, used by recycler view
    private static final DateTimeFormatter sSpecialFormatter =
            DateTimeFormatter.ofPattern("dd MMMM HH:mm");

    public static Instant parseDateTimeStringToInstant(String date){
        try {
            // parse the "date" parameter, as ZonedDateTime
            ZonedDateTime zdt = ZonedDateTime.parse(
                    date,
                    // take into account the full date time formatter, and the appropriate TimeZone
                    sDateTimeFormatter.withZone(sLocaleZone)
            );
            // return the corresponding Instant
            return zdt.toInstant();
        } catch(DateTimeParseException e){
            // always log the exception
            Log.e(TAG, "DateTimeParseException", e);
            // return null
            return null;
        }
    }

    public static Instant parseDateStringToInstant(String date){
        try {
            // parse the "date" parameter, as ZonedDateTime
            // take into account the full date formatter, and the appropriate TimeZone
            LocalDate localDate = LocalDate.parse( date, sDateFormatter);
            ZonedDateTime zdt = localDate.atStartOfDay(sLocaleZone);
            // return the corresponding Instant
            return zdt.toInstant();
        } catch(DateTimeParseException e){
            // always log the exception
            Log.e(TAG, "DateTimeParseException", e);
            // return null
            return null;
        }
    }

    public static Instant parseDateTimeOrDateOrReturnNow(String date){
        Instant ret;
        if (
            // try to parse the "date" parameter, as date and time
                (ret = DateEasy.parseDateTimeStringToInstant(date)) == null
                        &&
                        // if the first parsing failed, try to parse as date
                        (ret = DateEasy.parseDateStringToInstant(date)) == null
        ) {
            // if nothing matches, return now()
            ret = DateEasy.now();
        }
        return ret;
    }

    public static Instant now(){
        return Instant.now();
    }

    public static Instant computeInstantFromLocalDate(int year, int month, int day) {
        return ZonedDateTime
                .now(sLocaleZone)
                .withYear(year)
                // instant API start at month 1, not 0
                .withMonth(month+1)
                .withDayOfMonth(day)
                .toInstant();
    }

    public static Instant mergeInstantAndLocalZonedTime(Instant date, int hour, int minute) {
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt
                .withHour(hour)
                .withMinute(minute)
                .withSecond(0)
                .toInstant();
    }

    public static int getZonedInstantYear(Instant date) {
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.getYear();
    }
    public static int getZonedInstantMonth(Instant date){
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.getMonthValue() - 1;
    }
    public static int getZonedInstantDay(Instant date){
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.getDayOfMonth();
    }
    public static int getZonedInstantHour(Instant date){
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.getHour();
    }
    public static int getZonedInstantMinute(Instant date){
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.getMinute();
    }

    public static Instant plusOneYear(Instant date){
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.plusYears(1).toInstant();
    }

    public static Instant endOfDay(Instant date){
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.withHour(23).withMinute(59).withSecond(59).toInstant();
    }

    public static Instant startOfDay(Instant date){
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.withHour(0).withMinute(0).withSecond(0).toInstant();
    }

    public static Instant plusDays(Instant date, int days) {
        ZonedDateTime zdt = date.atZone(sLocaleZone);
        return zdt.plusDays(days).toInstant();
    }

    public static String localeDateTimeStringFromNow(){
        return localeDateTimeStringFromInstant(now());
    }

    public static String localeDateTimeStringFromInstant(Instant instant){
        if(instant == null) return null;
        ZonedDateTime zdt = instant.atZone(sLocaleZone);
        return zdt.format(sDateTimeFormatter);
    }

    public static String localeSpecialStringFromInstant(Instant instant){
        ZonedDateTime zdt = instant.atZone(sLocaleZone);
        return zdt.format(sSpecialFormatter);
    }

}

