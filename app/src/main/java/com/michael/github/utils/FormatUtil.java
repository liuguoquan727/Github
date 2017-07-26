package com.michael.github.utils;

import com.mdroid.utils.Ln;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 各种格式化
 */
public class FormatUtil {

  public static final long MINUTE = 60 * 1000L;
  public static final long HOUR = 60 * MINUTE;
  public static final long DAY = 24 * HOUR;
  public static final long DAY2 = 2 * DAY;
  public static final long WEEK = 7 * DAY;
  public static final long MONTH = 31 * DAY;
  public static final long YEAR = (long) (365.24 * DAY);

  public static final String FORMAT_YYYY = "yyyy";
  public static final String FORMAT_MM_DD = "MM-dd";
  public static final String FORMAT_MM_DD_CHINA = "MM月dd日";
  public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
  public static final String FORMAT_YYYY_MM_DD_CHINA = "yyyy年MM月dd日";
  public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
  public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

  /**
   * 格式化时间
   *
   * @param date 时间
   * @param format 格式
   */
  public static String formatDate(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
    return sdf.format(date);
  }

  /**
   * 格式化时间
   *
   * @param time 时间
   * @param format 格式
   */
  public static String formatDate(long time, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
    return sdf.format(new Date(time));
  }

  /**
   * 转换时间格式
   *
   * @param str 时间字符串
   * @param format 原时间格式
   * @param newFormat 转换后的格式
   * @return 转换格式后的时间字符串, 如果原字符串不是时间，则返回空字符串
   */
  public static String formatDate(String str, String format, String newFormat) {
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
    String date = "";
    try {
      Date d = sdf.parse(str);
      date = formatDate(d.getTime(), newFormat);
    } catch (ParseException e) {
      Ln.e(e);
    }
    return date;
  }

  /**
   * 将日期转换为time
   *
   * @param date 时间字符串
   * @param format 时间格式
   */
  public static long date2time(String date, String format) {
    long time = 0L;
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
    try {
      Date d = sdf.parse(date);
      time = d.getTime();
    } catch (ParseException e) {
      Ln.e(e);
    }
    return time;
  }

  /**
   * 将日期转换为time
   *
   * @param dateStr 时间字符串
   * @param format 时间格式
   */
  public static Date getDate(String dateStr, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
    Date date = null;
    try {
      date = sdf.parse(dateStr);
    } catch (ParseException e) {
      Ln.e(e);
    }
    return date;
  }

  /**
   * 135***1111形式
   * @param phone
   * @return
   */
  public static String formatMobile(String phone) {
    return String.format("%s****%s",phone.substring(0,3),phone.substring(7,phone.length()));
  }

}
