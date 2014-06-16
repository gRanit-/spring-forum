package spring.forum.controllers;
//package default_package;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = Calendar.getInstance().getTime();
        return df.format(date);
    }

    public static String getEscapedCurrentDate() {
        return getCurrentDate().replace("/", "_").replace(" ", "_").replace(":", "_");
    }

    public static String getEscapedDate(String date) {
        return date.replace("/", "_").replace(" ", "_").replace(":", "_");
    }
    public static Date stringToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
