package kim.uno.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmSSS";

    public static Date parse(String date) {
        return parse(date, DEFAULT_FORMAT);
    }

    public static Date parse(String date, String fromFormat) {

        if (date == null || date.trim().length() == 0) return null;

        Date result = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromFormat);
            result = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            LogUtil.e(e);
        }

        return result;
    }

    public static String format(String date, String toFormat) {
        return format(date, DEFAULT_FORMAT, toFormat);
    }

    public static String format(String date, String fromFormat, String toFormat) {
        return format(parse(date, fromFormat), toFormat);
    }

    public static String format(Date date, String toFormat) {
        String result = null;
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(toFormat, Locale.US);
            result = simpleDateFormat.format(date);
        }
        return result;
    }

    public static String getNow() {
        return getNow(DEFAULT_FORMAT);
    }

    public static String getNow(String toFormat) {
        return format(new Date(), toFormat);
    }
}