package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间工具类
 */
public class DateUtil {

    public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
    /**
     * 获取当前日期
     */
    public static String getCurrent() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 获取当前日期
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 获取当前日期
     */
    public static String getCurrentDateMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(new Date());
    }


    /**
     * 获取明天日期
     */
    public static String getTomorrowDate() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * 获取昨天日期
     */
    public static String getYestardayDate() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * 转换年月
     */
    public static String getTimeForMoth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }

    /**
     * 转换年月
     */
    public static String getTimeForMoth2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }
    /**
     * 转换年月
     */
    public static String getTimeForMoth3(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }
    /**
     * 转换年月
     */
    public static String getTimeForMoth4(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 时间戳转换年月日
     */
    public static String getTimeForDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date mydate = new Date(time * 1000);
        return format.format(mydate);
    }


    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String getCurrentDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(new Date());
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前日
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    /**
     * 将时间戳转化为字符串
     *
     * @param showTime
     * @return
     */
    public static String formatTime2String(long showTime) {
        return formatTime2String(showTime, false);
    }

    public static String formatTime2String(long showTime, boolean haveYear) {
        String str = "";
        long distance = System.currentTimeMillis() / 1000 - showTime;
        if (distance < 300) {
            str = "刚刚";
        } else if (distance >= 300 && distance < 600) {
            str = "5分钟前";
        } else if (distance >= 600 && distance < 1200) {
            str = "10分钟前";
        } else if (distance >= 1200 && distance < 1800) {
            str = "20分钟前";
        } else if (distance >= 1800 && distance < 2700) {
            str = "半小时前";
        } else if (distance >= 2700) {
            Date date = new Date(showTime * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = formatDateTime(sdf.format(date), haveYear);
        }
        return str;

    }

    public static String formatDateTime(String time, boolean haveYear) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (time == null) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + time.split(" ")[1];
        } else {
            if (haveYear) {
                int index = time.indexOf(" ");
                return time.substring(0, index);
            } else {
                int yearIndex = time.indexOf("-") + 1;
                String substring = time.substring(yearIndex, time.length());
                //去掉秒
                return substring.substring(0, substring.length()-3);
            }
        }
    }

    /**
     * 把秒转换为时分
     */
    public static String formatDateBySec(long mss) {
        String DateTimes = "";
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        String s = seconds +"";
        if (seconds < 10)
            s = 0+s;
        if (minutes > 0) {
            DateTimes = minutes + ":"
                    + s;
        } else {
            DateTimes = "0:" + s;
        }

        return DateTimes;
    }

    public static int getDayLength(String start_date,String end_date) throws Exception{

        Date fromDate = getStrToDate(start_date,DATE_FORMAT_NORMAL);  //开始日期
        Date toDate = getStrToDate(end_date,DATE_FORMAT_NORMAL); //结束日期
        long from = fromDate.getTime();
        long to = toDate.getTime();

        //一天等于多少毫秒：24*3600*1000

        int day = (int)((to-from)/(24*60*60*1000));
        return day;
    }

    private static Date getStrToDate(String date,String fomtter) throws Exception{
        DateFormat df = new SimpleDateFormat(fomtter);
        return df.parse(date);
    }


}
