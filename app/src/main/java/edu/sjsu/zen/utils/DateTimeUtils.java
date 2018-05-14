package edu.sjsu.zen.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static int getDayOfWeek(String dayName) {
        if (dayName.equals("Sunday") || dayName.equals("Sun")) {
            return 1;
        } else if (dayName.equals("Monday") || dayName.equals("Mon")) {
            return 2;
        } else if (dayName.equals("Tuesday") || dayName.equals("Tue")) {
            return 3;
        } else if (dayName.equals("Wednesday") || dayName.equals("Wed")) {
            return 4;
        } else if (dayName.equals("Thursday") || dayName.equals("Thu")) {
            return 5;
        } else if (dayName.equals("Friday") || dayName.equals("Fri")) {
            return 6;
        } else if (dayName.equals("Saturday") || dayName.equals("Sat")) {
            return 7;
        } else {
            return -1;
        }
    }

    public static long getNextEventDateTimeInMillis(String weekDay, String beginTime) {
        int dayOfWeek = getDayOfWeek(weekDay);
        Calendar calendar = Calendar.getInstance();

        String strDateFormat = "HH:mmaa";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat, Locale.US);
        try {
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            int year = calendar.get(Calendar.YEAR);

            Date date = sdf.parse(beginTime);
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear + 1);
                calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            }
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            return -1;
        }
    }

    public static long getNextEventDateTimeInMillis(String dueDate) {
        Calendar calender = Calendar.getInstance();
        String strDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat, Locale.US);
        try{
        Date date = sdf.parse(dueDate);
        calender.setTime(date);
        calender.get(Calendar.YEAR);
        //calender.get(Calendar.WEEK_OF_YEAR);
        //calender.get(Calendar.DATE);
//        calender.get(Calendar.HOUR);
//        calender.get(Calendar.MINUTE);
//        calender.get(Calendar.SECOND);
        return calender.getTimeInMillis();

        }
        catch(ParseException e) {
            return -1;
        }


    }
}
