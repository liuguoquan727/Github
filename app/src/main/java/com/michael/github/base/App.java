package com.michael.github.base;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.mdroid.DBUtils;
import com.mdroid.lib.core.base.BaseApp;
import com.michael.github.utils.HttpUtils;

/**
 * Description：
 */
public class App extends BaseApp {
  private static final String KEY_USER = App.class.getCanonicalName() + ".user";
  private static final String KEY_TOKEN = App.class.getCanonicalName() + ".token";
  @SuppressLint("StaticFieldLeak") private static App mAppInstance;
  private String mToken;

  public static App getInstance() {
    return mAppInstance;
  }

  @Override public void onCreate() {
    super.onCreate();
    mAppInstance = this;
  }

  @Override public boolean isDebug() {
    return false;
  }

  public String getAppAndDeviceInfo() {
    return HttpUtils.getAppInfo();
  }

  public synchronized String getToken() {
    if (mToken == null) {
      mToken = DBUtils.read(KEY_TOKEN);
    }
    return mToken;
  }

  public synchronized void setToken(String token) {
    mToken = token;
    DBUtils.write(KEY_TOKEN, token);
  }

  public synchronized void logout() {
    mToken = null;
    DBUtils.delete(KEY_USER);
    DBUtils.delete(KEY_TOKEN);
  }

  public synchronized boolean isLogin() {
    return !TextUtils.isEmpty(getToken());
  }

  public String getAppToken() {
    return "";
  }
}
