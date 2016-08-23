package com.code.space.androidlib.utilities.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateTimeUtils {
    public static final DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    public static String formatYYYY_MM_DD_HH_MM_SS(Date target) {
        return format(target, new SimpleDateFormat(DateTimeUtils.YYYY_MM_DD_HH_MM_SS));
    }


    public static Date parseYYYY_MMM_DD_HH_MM_SS(String timeString) {
        return parse(timeString, new SimpleDateFormat(DateTimeUtils.YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 将日期转化为string字符串，如果Date为null，返回"";
     *
     * @param target    日期
     * @param formatter simpleDateFormat
     * @return string字符串
     */
    public static String format(Date target, SimpleDateFormat formatter) {
        if (target == null) return "";
        return formatter.format(target);
    }


    public static Date parse(String target, SimpleDateFormat formatter) {
        if (target != null) {
            Date date = null;
            try {
                date = formatter.parse(target);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return new Date();
    }


}
