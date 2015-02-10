package rbhat.saiyajin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rbhat on 9/1/15.
 */
public class Utilities {

    public static String getDateString(long dateLong) {

        Date date = new Date(dateLong);
        return getDateString(date);
    }

    public static String getDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String curDateString = formatter.format(date);
        return curDateString;
    }

    public static long getDateLong(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String curDateString = formatter.format(date);
        long dateInteger = 0;

        try {
            date = formatter.parse(curDateString);
        } catch (ParseException e) {
        }

        dateInteger = date.getTime();
        return dateInteger;
    }
}
