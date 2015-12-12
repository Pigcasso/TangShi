package io.liamju.tangshi.utils;

import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * @author goodev
 * @since 15/11/3
 */
public class FullDateManager {
    public final static String YEAR_CHINESE = "年";
    public final static String MONTH_CHINESE = "月";
    public final static String DAY_CHINESE = "日";
    private static final HashMap<Integer, String> intToChinese =
            new HashMap<>();

    static {
        intToChinese.put(0, "零");
        intToChinese.put(1, "一");
        intToChinese.put(2, "二");
        intToChinese.put(3, "三");
        intToChinese.put(4, "四");
        intToChinese.put(5, "五");
        intToChinese.put(6, "六");
        intToChinese.put(7, "七");
        intToChinese.put(8, "八");
        intToChinese.put(9, "九");
        intToChinese.put(10, "十");
    }

    private HashMap<Integer, String> yearMap = new HashMap<>();
    private HashMap<Integer, String> monthMap = new HashMap<>();
    private HashMap<Integer, String> dayMap = new HashMap<>();

    private static String yearToChinese(int year) {
        StringBuilder yearSb = new StringBuilder();
        while (year > 0) {
            int y = year % 10;
            yearSb.insert(0, intToChinese.get(y));
            year /= 10;
        }
        return yearSb.toString();
    }

    public static String getYear(int year) {
        return getPureYear(year) + YEAR_CHINESE + " ";
    }

    public static String getMonth(int month) {
        return getPureMonth(month) + MONTH_CHINESE + " ";
    }

    private static String getPureMonth(int month) {
        return otherToChinese(month);
    }

    private static String otherToChinese(int dayOrMonth) {
        if (dayOrMonth < 0) {
            return "";
        }
        if (dayOrMonth < 10) {
            return intToChinese.get(dayOrMonth);
        }
        String otherSb;
        int tens = dayOrMonth / 10;
        otherSb = ((tens == 1 ? "" : intToChinese.get(tens)) + "十");
        int units = dayOrMonth % 10;
        otherSb += (units == 0 ? "" : intToChinese.get(units));
        return otherSb;
    }

    private static String getPureYear(int year) {
        return yearToChinese(year);
    }

    public static String getDay(int day) {
        return getPureDay(day) + DAY_CHINESE + " ";
    }

    private static String getPureDay(int day) {
        return otherToChinese(day);
    }

    public static long getDateSeconds(DateTime current) {
        return getDateSeconds(current.getYear(),
                current.getMonthOfYear(),
                current.getDayOfMonth());
    }

    public static long getDateSeconds(int year, int month, int day) {
        DateTime dateTime = new DateTime(year, month, day, 0, 0);
        return dateTime.getMillis() / 1000;
    }

    /**
     * @return format:"二零一五年 十一月 十八日"
     */
    public static String getFullDate() {
        return getFullDate(new DateTime().getMillis() / 1000);
    }

    public static long getTodayDateSecond() {
        DateTime now = new DateTime();
        DateTime today = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0);
        return today.getMillis() / 1000;
    }

    public static String getFullDate(long createdTime) {
        DateTime dateTime = new DateTime(createdTime * 1000);
        StringBuilder sb = new StringBuilder("");
        sb.append(getYear(dateTime.getYear()));
        sb.append(getMonth(dateTime.getMonthOfYear()));
        sb.append(getDay(dateTime.getDayOfMonth()));
        return sb.toString();
    }

    /**
     * 获取当前时间的毫秒数
     *
     * @return 当前时间的毫秒数
     */
    public static long getFullDateMills() {
        return DateTime.now().getMillis();
    }
}
