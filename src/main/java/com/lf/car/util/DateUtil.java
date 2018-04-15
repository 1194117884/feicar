package com.lf.car.util;


import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Date date_1000 = null;

    static {
        try {
            date_1000 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse("1000-01-01 00:00:00");
        } catch (ParseException e) {
        }
    }

    public static Date get1000Date() {
        return date_1000;
    }

    public static Date parse(String timeStr) {
        return parse("yyyy-MM-dd HH:mm:ss", timeStr);
    }

    public static Date parse(String pattern, String timeStr) {
        if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(timeStr)) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).parse(timeStr);
        } catch (ParseException e) {
        }
        return null;
    }

    public static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formatDay(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String formatDayHour(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH").format(date);
    }

    public static String formatHour(Date date) {
        return new SimpleDateFormat("HH").format(date);
    }

    public static String format(String pattern, Date date) {
        if (null == date) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 取得下一个更新统计缓存时间，即明日0点
     *
     * @return
     */
    public static Date nextTaskTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 取得当前月份
     *
     * @return
     */
    public static Date getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 取得当日0点
     *
     * @return
     */
    public static Date getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, sec);
        return calendar.getTime();
    }

    public static Date addMinute(Date date, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date addDayAndZeroTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date addDayAndMaxTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 59);
        //将秒至0
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static String getDateAndZeroTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        return DateUtil.format(calendar.getTime());
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(Date pTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(pTime);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 获取当前时间某一天日期
     *
     * @param date
     * @return lianzeng
     * 2017年6月30日
     */
    public static Date getNextDay(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前时间后三天的日期
     *
     * @param date
     * @return lianzeng
     * 2017年6月30日
     */
    public static Date getNextThreeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        date = calendar.getTime();
        return date;
    }

    public static int getDayWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static Date threeDaysLater(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 3);
        return calendar.getTime();
    }

    public static int getHourOfDay() {
        return getHourOfDay(new Date());
    }

    public static int getHourOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getDayHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Date getDate(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static Date getPreDate(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n);
        return calendar.getTime();
    }

    public static Date getNextDayDate(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date twoHoursLater(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        return calendar.getTime();
    }

    public static Date oneHoursLater(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }

    public static Date addHours(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static Date getMonthOneDate(int next) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, next);
        return calendar.getTime();
    }

    public static Date get0Dian(Date date, int next) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, next);
        return calendar.getTime();
    }

    /**
     * 指定注入日期中的【时分秒】
     *
     * @param dateTime
     * @param time
     * @return
     */
    public static Date replaceTime(Date dateTime, String time) {
        try {
            String s = format("yyyy-MM-dd#HH:mm:ss", dateTime);
            s = s.substring(0, s.indexOf("#")) + time;
            return parse("yyyy-MM-ddHH:mm:ss", s);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取当前时间的年月日
     */
    public static String getMonthDay() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        return String.valueOf(year) + (month < 10 ? 0 + String.valueOf(month) : String.valueOf(month)) + (day < 10 ? 0 + String.valueOf(day) : String.valueOf(day));
    }

    public static String getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 0);
        Date time = cal.getTime();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        String format = sd.format(time);
        return format;
    }

    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static void main(String[] args) throws Exception {
        //String s="20180101";
        //SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        //Date parse = sd.parse(s);
        Date nextDay = getNextDay(new Date(), 200);
        String year = getYear(nextDay);
        System.out.println(year);
    }

}
