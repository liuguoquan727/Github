package com.michael.github.module.repository.content;

import android.util.Log;
import android.view.View;
import com.mdroid.lib.core.eventbus.EventBus;
import com.mdroid.lib.core.eventbus.EventBusEvent;
import com.squareup.otto.Subscribe;

/**
 * Created by liuguoquan on 2017/5/25.
 */

public class JavaFragment extends BaseRepositoryFragment {

  @Override public String getType() {
    return "language:java";
  }

  @Override protected void initView(View parent) {
    super.initView(parent);
    EventBus.bus().post(new EventBusEvent(1,"123"));
  }

  @Subscribe @Override public void onNotify(EventBusEvent event) {
    super.onNotify(event);
    Log.d("lgq", "onNotify: "+event.getType()+";"+event.getExtra());
  }
}
