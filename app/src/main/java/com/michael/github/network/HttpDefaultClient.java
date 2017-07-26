package com.michael.github.network;

import com.mdroid.lib.core.http.HttpClient;
import com.michael.github.base.App;
import java.io.IOException;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 匿名 SSL HttpDefaultClient
 */
public class HttpDefaultClient {
  public static OkHttpClient getDefaultHttpClient() {
    OkHttpClient.Builder builder = HttpClient.getDefaultHttpClient().newBuilder();
    List<Interceptor> interceptors = builder.interceptors();
    interceptors.add(0, new Interceptor() {
      public Response intercept(Chain chain) throws IOException {
        okhttp3.Request.Builder builder = chain.request()
            .newBuilder();
        builder.addHeader("Authorization","123");
        builder.addHeader("content-type", "application/json");
        String token = App.getInstance().getAppToken();
        if (token != null) {
          builder.addHeader("accessToken", token);
        }
        return chain.proceed(builder.build());
      }
    });
    interceptors.add(new TokenValidationInterceptor());
    return builder.build();
  }

  public static class TokenValidationInterceptor implements Interceptor {

    @Override public Response intercept(Chain chain) throws IOException {
      Response response = chain.proceed(chain.request());
      ResponseBody peekBody = response.peekBody(Integer.MAX_VALUE);
      //ApiResult result = App.getGson().fromJson(peekBody.string(), ApiResult.class);
      //if (result.isTokenInvalid()) {
      //  // 登录信息过期，跳转到登录界面
      //  App app = App.getInstance();
      //  result.message = app.getString(R.string.error_login_invalid);
      //  app.logout();
      //  Intent intent = new Intent(app, BaseActivity.class);
      //  intent.putExtra(BaseActivity.FRAGMENT_NAME, LoginFragment.class.getName());
      //  intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      //  Activity activity = app.getTopActivity();
      //  activity.startActivity(intent);
      //  activity.finish();
      //}
      return response;
    }
  }
}
