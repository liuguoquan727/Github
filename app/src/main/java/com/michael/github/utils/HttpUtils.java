package com.michael.github.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.mdroid.utils.Ln;
import com.mdroid.utils.Md5;
import com.michael.github.base.App;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class HttpUtils {
  private static final String KEY_DEVICE_ID = HttpUtils.class.getCanonicalName() + ".device_id";
  public static String DEVICE_ID;
  private static String DEVICE_ID_INFO;
  private static String ANDROID_ID;

  public static String format(Map<String, Object> params) {
    try {
      final StringBuilder result = new StringBuilder();
      Set<String> keys = params.keySet();
      for (final String key : keys) {
        final Object value = params.get(key);
        if (value == null) continue;
        if (result.length() > 0) result.append("&");
        result.append(URLEncoder.encode(key, "UTF-8"));
        result.append("=");
        result.append(URLEncoder.encode(value.toString(), "UTF-8"));
      }
      return result.toString();
    } catch (UnsupportedEncodingException e) {
      // Impossible!
      throw new IllegalArgumentException(e);
    }
  }

  public static Map<String, String> getHeader() {
    Map<String, String> map = new HashMap<String, String>();
    String token = App.getInstance().getToken();
    if (!TextUtils.isEmpty(token)) map.put("TOKEN", token);
    map.put("device", getAppInfo());
    return map;
  }

  public static String getAppInfo() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("client", "android");
    map.put("os_version", Build.VERSION.SDK_INT + "");
    map.put("ver", getVersionCode() + "");
    map.put("device_id", getAndroidId());
    map.put("network", getNetworkType());
    map.put("appName", "zhuangzhu");
    return format(map);
  }

  public static String getIMSI() {
    TelephonyManager telephonyMgr = (TelephonyManager) App.Instance()
        .getApplicationContext()
        .getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyMgr.getSubscriberId();
  }

  public static String getIMEI() {
    TelephonyManager telephonyMgr = (TelephonyManager) App.Instance()
        .getApplicationContext()
        .getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyMgr.getDeviceId();
  }

  public static String getMAC() {
    WifiManager wifi =
        (WifiManager) App.Instance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = wifi.getConnectionInfo();
    return info.getMacAddress();
  }

  public static String getDeviceIdInfo() {
    if (DEVICE_ID_INFO != null) {
      return DEVICE_ID_INFO;
    }
    JSONObject json = new JSONObject();
    TelephonyManager tm = (TelephonyManager) App.Instance()
        .getApplicationContext()
        .getSystemService(Context.TELEPHONY_SERVICE);
    WifiManager wm =
        (WifiManager) App.Instance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    try {
      json.put("DEVICEID", tm.getDeviceId());
      json.put("SIMNO", tm.getSimSerialNumber());
      json.put("IMSI", tm.getSubscriberId());
      json.put("ANDROID_ID", Settings.Secure.getString(App.Instance().getContentResolver(),
          Settings.Secure.ANDROID_ID));
      json.put("SERIAL", getSerial());
      json.put("MAC", wm != null ? wm.getConnectionInfo().getMacAddress() : "");
      json.put("RANDOM", "");
      DEVICE_ID_INFO = json.toString();
      return DEVICE_ID_INFO;
    } catch (Exception e) {
      Ln.e(e);
    }
    return "";
  }

  public static String getAndroidId() {
    if (ANDROID_ID != null) {
      return ANDROID_ID;
    }
    TelephonyManager manager =
        (TelephonyManager) App.Instance().getSystemService(Context.TELEPHONY_SERVICE);
    String str = "\"DEVICEID\":\""
        + manager.getDeviceId()
        + "\"-"
        + "\"ANDROID_ID\":\""
        + Settings.Secure.getString(App.Instance().getContentResolver(), Settings.Secure.ANDROID_ID)
        + "\"";
    ANDROID_ID = "IMEI_" + Md5.MD5(str);
    return ANDROID_ID;
  }

  private static String getSerial() {
    String serial = "";
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
      serial = Build.SERIAL;
    } else {
      try {
        Class spClass = Class.forName("android.os.SystemProperties");
        serial = (String) spClass.getMethod("get", new Class[] { String.class })
            .invoke(spClass, "ro.serialno");
      } catch (Exception e) {
        Ln.e(e);
      }
    }
    return serial;
  }

  public static String getNetworkType() {
    NetworkInfo info = ((ConnectivityManager) App.Instance()
        .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    if (info != null) {
      int i = info.getType();
      if (i == 0) {
        String str = info.getSubtypeName();
        if (str != null) {
          return str.replace(" ", "");
        }
        return "GPRS";
      }
      if (i == 1) {
        return "WIFI";
      }
    }
    return "UNKNOWN";
  }

  public static String getMetaDataValue(String text) {
    PackageManager localPackageManager = App.Instance().getPackageManager();
    Object o;
    try {
      ApplicationInfo localApplicationInfo =
          localPackageManager.getApplicationInfo(App.Instance().getPackageName(),
              PackageManager.GET_META_DATA);
      o = null;
      if (localApplicationInfo != null) {
        Bundle data = localApplicationInfo.metaData;
        o = null;
        if (data != null) {
          o = localApplicationInfo.metaData.get(text);
        }
      }
      if (o == null) {
        throw new RuntimeException(
            "The name '" + text + "' is not defined in the manifest file's meta data.");
      }
    } catch (PackageManager.NameNotFoundException e) {
      throw new RuntimeException("Could not read the name in the manifest file.", e);
    }
    return o.toString();
  }

  public static int getVersionCode() {
    try {
      PackageManager packageManager = App.getInstance().getPackageManager();
      PackageInfo packInfo = packageManager.getPackageInfo(App.getInstance().getPackageName(), 0);
      return packInfo.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      // Impossible
    }
    return 0;
  }
}
