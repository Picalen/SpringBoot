package com.personal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @author Rocky
 * 时区转换类
 * PS: 时间字符类型 指可转换时区的字符串
 */
public class DateFormat {

    private static Logger logger = LoggerFactory.getLogger(DateFormat.class);

    public final static String MOMENT;

    public final static String DATETIME;

    public final static String DATE;

    public final static String TIME;

    static {
        MOMENT = "yyyy-MM-dd HH:mm:ss.SSS";
        DATETIME = "yyyy-MM-dd HH:mm:ss";
        DATE = "yyyy-MM-dd";
        TIME = "HH:mm:ss";
    }

    /**
     * GMT时区--转换--CST时区
     *
     * @param str 时间字符类型
     * @return java.util.Date
     * @throws Exception
     */
    public static Date formatMoment(String str) throws Exception {
        try {
            Instant instant = Instant.parse(str);
            return new Date(instant.toEpochMilli());
        } catch (Exception e) {
            logger.error("转换时区异常:\n" + e.getMessage());
            throw new Exception("转换时区异常");
        }
    }

    /**
     * GMT时区--转换--CST时区
     *
     * @param str    时间字符类型
     * @param format 需要转换的时间格式
     * @return java.lang.String 格式化后时间字符串
     * @throws Exception
     */
    public static String formatMoment(String str, String format) throws Exception {
        Instant instant;
        try {
            instant = Instant.parse(str);
        } catch (Exception e) {
            logger.error("转换时区异常:\n" + e.getMessage());
            throw new Exception("转换时区异常");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(new Date(instant.toEpochMilli()));
        } catch (Exception e) {
            logger.error("格式化时间异常:\n" + e.getMessage());
            throw new Exception("格式化时间异常");
        }
    }

    /**
     * CST时区--转换--GMT时区
     *
     * @param date 时间对象
     * @return java.lang.String 时间字符类型
     * @throws Exception
     */
    public static String returnMoment(Date date) throws Exception {
        try {
            return date.toInstant().toString();
        } catch (Exception e) {
            logger.error("转换时区异常:\n" + e.getMessage());
            throw new Exception("转换时区异常");
        }
    }

    /**
     * 格式化时间
     *
     * @param date   时间对象
     * @param format 需要转换的时间格式
     * @return
     * @throws Exception
     */
    public static String returnFormatDate(Date date, String format) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            logger.error("格式化时间异常:\n" + e.getMessage());
            throw new Exception("格式化时间异常");
        }
    }
}
