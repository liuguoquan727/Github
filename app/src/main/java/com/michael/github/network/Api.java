package com.michael.github.network;

import android.support.annotation.NonNull;
import com.mdroid.lib.core.http.HttpClient;
import com.michael.github.base.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public class Api {

  private static Retrofit sNormalRetrofit;

  public static SearchApi getSearchApi() {

    return getRetrofit().create(SearchApi.class);

  }

  @NonNull private static Retrofit getRetrofit() {
    if (sNormalRetrofit == null) {
      sNormalRetrofit = new Retrofit.Builder().baseUrl(Constants.HOST)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(HttpClient.getDefaultHttpClient())
          .build();
    }
    return sNormalRetrofit;
  }

}
