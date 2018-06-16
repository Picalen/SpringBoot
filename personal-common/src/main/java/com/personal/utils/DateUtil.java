package com.personal.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author  daila
 * @date  2017/12/28.
 */
public class DateUtil {
    public static final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat ymd = new SimpleDateFormat(
            "yyyy-MM-dd");
    public final static String DEF_FORMAT_NOTIME = "yyyy-MM-dd";

    /**
     * 日期字符串转成java.util.Date
     * */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    public static Date parseDate(String dateStr, String format) {
        if (isEmpty(dateStr)) {
            return null;
        }
        Date date = null;
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat(format);
            String dt = dateStr;
            if ((!"".equals(dt)) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
                        "0");
            }
            date = (java.util.Date) df.parse(dt);
        } catch (Exception e) {
        }
        return date;
    }

    public static Date parseDate(String dateStr,
                                 SimpleDateFormat format) {
        if (isEmpty(dateStr)) {
            return null;
        }
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期上下浮动days，单位field
     *
     * @param date
     * @param field Calendar 格式
     * @param days
     * @return
     */
    public static Date addOrSubDate(Date date,int field,int days) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        c.add(field, days);
        return c.getTime();

    }


	/*
	 * 获取当天日期
	 * */

    public static Date today(String format) {
        return parseDate(todayStr(format), format);
    }


    public static String todayStr(String format) {
        return formatDateToStr(new Date(), format);
    }

    /**
     * 生成默认格式的日期
     *
     * @param date
     * @return
     */
    public static String formatDateToStr(Date date) {
        return formatDateToStr(date, "yyyy-MM-dd");
    }





    /**
     * @param date
     *            需要格式化的日期對像
     * @param formatter
     *            格式化的字符串形式
     * @return 按照formatter指定的格式的日期字符串
     * @throws ParseException
     *             無法解析的字符串格式時拋出
     */
    public static String formatDateToStr(Date date, String formatter) {
        if (date == null) {
            return "";
        }
        try {
            return new java.text.SimpleDateFormat(formatter).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 將日期按照指定的模式格式化
     *
     * @param date
     *            {@link Date}
     * @param format
     *            如：yyyy/MM/dd
     * @return 返回空表示格式化產生異常
     */
    public static String format(java.util.Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                java.text.DateFormat df = new java.text.SimpleDateFormat(format);
                result = df.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 将一种字符日期转化成另外一种日期格式
     *
     * @param date
     * @param fmtSrc
     * @param fmtTag
     * @return
     */
    public static String format(String date, String fmtSrc, String fmtTag) {
        if (null == fmtSrc) {
            return null;
        }
        if (fmtSrc.equals(fmtTag)) {
            return date;
        }
        String year, month, daty;
        int y, m, d;
        fmtSrc = fmtSrc.toUpperCase();
        y = fmtSrc.indexOf("YYYY");
        m = fmtSrc.indexOf("MM");
        d = fmtSrc.indexOf("DD");
        if (y < 0 || m < 0 || d < 0) {
            return date;
        }
        year = date.substring(y, y + 4);
        month = date.substring(m, m + 2);
        daty = date.substring(d, d + 2);
        fmtTag = fmtTag.toUpperCase();
        fmtTag = fmtTag.replaceAll("YYYY", year);
        fmtTag = fmtTag.replaceAll("MM", month);
        fmtTag = fmtTag.replaceAll("DD", daty);
        return fmtTag;
    }

    /**
     * 計算指定年月的日期數
     *
     * @param year
     * @param month
     * @return
     */
    public static int maxDayOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                boolean isRunYear = (year % 400 == 0)
                        || (year % 4 == 0 && year % 100 != 0);
                return isRunYear ? 29 : 28;
            default:
                return 31;
        }
    }

    /**
     * 获取指定年月的日期數
     *
     * @param year
     * @param month
     * @return
     */
    public static int maxDayOfMonth(String year, String month) {
        return maxDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
    }

    /**
     * 获取指定年月上月月末日期
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastMonthDate(String year, String month) {
        return getLastMonthDate(Integer.parseInt(year), Integer.parseInt(month));
    }

    /**
     * 获取指定年月上月月末日期
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastMonthDate(int year, int month) {
        if (month <= 1) {
            year -= 1;
            month = 12;
        } else {
            month -= 1;
        }
        StringBuffer bfDate = new StringBuffer();
        bfDate.append(year);
        if (month < 10) {
            bfDate.append("0");
        }
        bfDate.append(month);
        bfDate.append(maxDayOfMonth(year, month));
        return bfDate.toString();
    }

    /**
     * 获取指定年月月末日期
     *
     * @param year
     * @param month
     * @return
     */
    public static String getMonthLastDate(int year, int month, String split) {
        StringBuffer bfDate = new StringBuffer();
        bfDate.append(year);
        bfDate.append(split);
        if (month < 10) {
            bfDate.append("0");
        }
        bfDate.append(month);
        bfDate.append(split);
        bfDate.append(maxDayOfMonth(year, month));
        return bfDate.toString();
    }
    /**
     * 提前N天的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date beforeDate(Date date, int days) {
        return addOrSubDate(date,-days);

    }

    /**
     * 延后N天的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date laterDate(Date date, int days) {
        return addOrSubDate(date,days);
    }

    /**
     * 日期上下浮动N天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addOrSubDate(Date date, int days) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        c.add(java.util.Calendar.DAY_OF_YEAR, days);
        return c.getTime();

    }

    /**
     * 日期上下浮动N月份
     *
     * @param date
     * @param month
     * @return
     */
    public static Date addOrSubMonth(Date date, int month) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        c.add(java.util.Calendar.MONTH, month);
        return c.getTime();

    }

    /**
     * 获取本月第一天
     * @return
     */
    public static String getMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return formatDateToStr(cal.getTime());
    }

    /**
     * 获取本年第一天
     * @return
     */
    public static String getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取某年第一天
     * @return
     */
    public static String getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return formatDateToStr(currYearFirst);
    }
    /**
     * 获取某年最后一天
     * @return
     */
    public static String getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return formatDateToStr(currYearLast);
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(parseDate(bdate));
        long time2 = cal.getTimeInMillis();
        long betweenDays=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 两个日期比较大小
     * @param first
     * @param last
     * @return 1表示first>last;-1表示first<last;0表示first==last
     */
    public static int compareDate(Date first,Date last){
        long firstTime = first.getTime();
        long lastTime = last.getTime();
        if(firstTime > lastTime){
            return 1;
        }else if(firstTime < lastTime){
            return -1;
        }else{
            return 0;
        }
    }
    /**
     * 获得两个日期之间的所有月份
     * @param minDate 较小的时间
     * @param maxDate  较大的时间
     * @return 获得两个日期之间的所有月份
     * @throws ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }
    /**
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        c.add(java.util.Calendar.HOUR_OF_DAY, hour);
        return c.getTime();
    }

    /**
     * 一周前的日期
     *
     * @return
     */
    public static Date getLastWeek() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.add(java.util.Calendar.DAY_OF_YEAR, -7);
        return c.getTime();

    }

    public static int curHour(Calendar cal) {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int curMinute(Calendar cal) {
        return cal.get(Calendar.MINUTE);
    }

    public static int curSecond(Calendar cal) {
        return cal.get(Calendar.SECOND);
    }

    public static String curTimeStr() {
        Calendar cal = Calendar.getInstance();
        // 分别取得当前日期的年、月、日
        StringBuffer bf = new StringBuffer(10);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            bf.append("0");
            bf.append(hour);
            bf.append(":");
        }
        int minite = cal.get(Calendar.MINUTE);
        if (minite < 10) {
            bf.append("0");
            bf.append(minite);
            bf.append(":");
        }
        int second = cal.get(Calendar.SECOND);
        if (second < 10) {
            bf.append("0");
            bf.append(second);
        }
        return bf.toString();
    }

    /***************************************************************************
     * @功能 计算当前日期某年的第几周
     * @return interger
     * @throws ParseException
     **************************************************************************/
    public static int getWeekNumOfYear() {
        Calendar calendar = Calendar.getInstance();
        int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
        return iWeekNum;
    }

    /***************************************************************************
     * @功能 计算指定日期某年的第几周
     * @return interger
     * @throws ParseException
     **************************************************************************/
    public static int getWeekNumOfYearDay(String strDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(strDate));
        int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
        return iWeekNum;
    }

    /***************************************************************************
     * @功能 计算某年某周的开始日期
     * @return interger
     * @throws ParseException
     **************************************************************************/
    public static String getWeekFirstDay(int yearNum, int weekNum) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // 分别取得当前日期的年、月、日
        String tempYear = Integer.toString(yearNum);
        String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String tempDay = Integer.toString(cal.get(Calendar.DATE));
        return tempYear + "-" + tempMonth + "-" + tempDay;
    }

    public static String getWeekFirstDay(int weekNum) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // 分别取得当前日期的年、月、日
        String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String tempDay = Integer.toString(cal.get(Calendar.DATE));
        return cal.get(Calendar.YEAR) + "-" + tempMonth + "-" + tempDay;
    }

    /***************************************************************************
     * @功能 计算某年某周的结束日期
     * @return interger
     * @throws ParseException
     **************************************************************************/
    public static String getWeekEndDay(int yearNum, int weekNum) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // 分别取得当前日期的年、月、日
        String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String tempDay = Integer.toString(cal.get(Calendar.DATE));
        return cal.get(Calendar.YEAR) + "-" + tempMonth + "-" + tempDay;
    }

    public static String getWeekEndDay(int weekNum) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // 分别取得当前日期的年、月、日
        String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String tempDay = Integer.toString(cal.get(Calendar.DATE));
        return cal.get(Calendar.YEAR) + "-" + tempMonth + "-" + tempDay;
    }

    public static String getDatePreHours(int preHours) {
        // 当前日期
        Date date = new Date();
        // 格式化对象
        SimpleDateFormat sdf = new SimpleDateFormat("yy"
                + " HH:mm:ss");
        // 日历对象
        Calendar calendar = Calendar.getInstance();
        // 设置当前日期
        calendar.setTime(date);
        // 小时增减
        calendar.add(Calendar.HOUR_OF_DAY, preHours);

        return sdf.format(calendar.getTime());
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getyyyyMMddDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public static boolean isEmpty(String value) {
        return null == value || "".equals(value.trim());
    }

    public static void main(String[] args) throws ParseException {
		/*System.out.println(getMonth());
		System.out.println(getMonthFirstDay());
		System.out.println(todayStr());
		System.out.println(getCurrYearFirst());*/
        //System.out.println(getMonthBetween("2015-01-01","2016-02-01"));
		/*System.out.println(addOrSubMonth(today(),13));
		System.out.println(DateUtil.todayStr().substring(todayStr().length()-2));*/
		/*System.out.print(curTimeStr());*/
		/*System.out.println(todayStr());
		System.out.println(DateUtil.curTimeStr());
		System.out.println(DateUtil.today());
		System.out.println(DateUtil.laterDate(DateUtil.today(), 1));
		Date dd = DateUtil.laterDate(DateUtil.today(), 1);
		System.out.println(DateUtil.addOrSubMonth(dd, 1));
		System.out.println(DateUtil.addOrSubMonth(dd, 2));*/
		/*System.out.println(DateUtil.parseDate("2015-03-29 16:37:36"));
		System.out.println(DateUtil.addOrSubMonth(DateUtil.parseDate("2015-03-29 16:37:36"), Consts.PWD_VALID_PERIOD));
		System.out.println(DateUtil.today());
		System.out.println(compareDate(addOrSubMonth(DateUtil.parseDate("2015-03-29 16:37:36"), Consts.PWD_VALID_PERIOD), today()));*/
        System.out.println(DateUtil.getDatePreHours(0));
    }
}
