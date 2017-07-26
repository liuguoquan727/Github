package com.michael.github.utils;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by ouyangzn on 2016/11/17.<br/>
 * Description：
 */
public class DialogUtil {

  /**
   * 获取一个PopupWindow
   *
   * @param contentView 内容view
   * @return PopupWindow
   */
  public static PopupWindow getPopupWindow(View contentView, @StyleRes int animStyle) {
    PopupWindow popupWindow = new PopupWindow(contentView);
    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
    popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    popupWindow.setOutsideTouchable(true);
    popupWindow.setFocusable(true);
    popupWindow.setAnimationStyle(animStyle);
    return popupWindow;
  }

  /**
   * dismiss一个PopupWindow
   *
   * @param pop PopupWindow
   * @return true表示正常dismiss， false表示PopupWindow本来就没显示
   */
  public static boolean dismissPopupWindow(PopupWindow pop) {
    if (pop != null && pop.isShowing()) {
      pop.dismiss();
      return true;
    }
    return false;
  }
}
