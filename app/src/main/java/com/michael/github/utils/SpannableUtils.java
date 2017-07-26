package com.michael.github.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by liuguoquan on 2016/11/23.
 */

public class SpannableUtils {

  public static SpannableString setSpannableTextColor(String title, String content) {
    return setSpannableTextColor(title, 0xff333333, content);
  }

  public static SpannableString setSpannableTextColor(String title,int titleColor,String content) {
    String text = title + content;
    SpannableString ss = new SpannableString(text);
    ss.setSpan(new ForegroundColorSpan(titleColor), 0, title.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    return ss;
  }
}
