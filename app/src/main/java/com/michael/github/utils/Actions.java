package com.michael.github.utils;

import android.content.Intent;
import com.michael.github.MainActivity;
import com.michael.github.base.App;

/**
 * Created by liuguoquan on 2016/11/17.
 */

public class Actions {

  public static void main() {
    Intent intent = new Intent(App.Instance(), MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    App.Instance().startActivity(intent);
  }
}
