package com.michael.github.utils;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.text.Spanned;
import android.widget.TextView;
import java.util.Locale;

import static android.text.Html.fromHtml;
import static java.lang.String.format;

/**
 * Html.fromHtml方法集
 */
public class HtmlUtil {

  /**
   * 返回着色包装后的Html代码字符串
   *
   * @param content
   * @param colorValue
   */
  public static String colorString(String content, int colorValue) {
    if (null == content) {
      content = "";
    }
    return format(Locale.CHINA, "<font color='%d'>%s</font>", colorValue, content);
  }

  /**
   * 返回着色包装后的Html代码字符串
   *
   * @param activity
   * @param content
   * @param colorRes
   */
  @ColorInt public static String colorString(Activity activity, String content,
      @ColorRes int colorRes) {
    return colorString(content, activity.getResources().getColor(colorRes));
  }

  /**
   * 返回着色包装后的Html代码
   *
   * @param content
   * @param color 颜色值如0xffff0000
   * @return html代码
   */
  public static Spanned color(String content, int color) {
    return fromHtml(colorString(content, color));
  }

  /**
   * 返回着色包装后的Html代码
   *
   * @param activity
   * @param content
   * @param colorResId 颜色资源ID如R.color.red
   */
  @ColorInt public static Spanned color(Activity activity, String content,
      @ColorRes int colorResId) {
    return color(content, activity.getResources().getColor(colorResId));
  }

  /**
   * 返回下划线包装后的Html代码字符串
   *
   * @param content
   */
  public static String underlineString(String content) {
    if (null == content) {
      content = "";
    }
    return format("<u>%s</u>", content);
  }

  /**
   * 返回下划线包装后的Html代码
   *
   * @param content
   */
  public static Spanned underline(String content) {
    return fromHtml(underlineString(content));
  }

  /**
   * 返回删除线包装后的Html代码，<del></del>及<s></s>无效果
   *
   * @param textView
   * @param content
   */
  public static void deleteLine(TextView textView, String content) {
    if (null == content) {
      content = "";
    }
    textView.setText(content);
    textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
  }

  /**
   * 返回连接的Html代码
   *
   * @param first
   * @param second
   */
  public static Spanned concat(String first, String second) {
    if (null == first) {
      first = "";
    }
    if (null == second) {
      second = "";
    }
    return fromHtml(format("%s%s", first, second));
  }

  /**
   * 加http头部
   *
   * @param website
   */
  public static String appendHttp(String website) {
    return format("http://%s", website);
  }

  /**
   * 加https头部
   *
   * @param website
   */
  public static String appendHttps(String website) {
    return format("https://%s", website);
  }
}
