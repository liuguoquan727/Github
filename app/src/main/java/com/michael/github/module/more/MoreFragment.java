package com.michael.github.module.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.OnClick;
import com.mdroid.app.TranslucentStatusCompat;
import com.mdroid.lib.core.base.ActivityLifecycle;
import com.mdroid.lib.core.base.BaseFragment;
import com.mdroid.lib.core.base.BasePresenter;
import com.mdroid.lib.core.base.Status;
import com.mdroid.lib.core.utils.ActivityUtil;
import com.mdroid.lib.core.utils.UIUtil;
import com.michael.github.R;
import com.michael.github.utils.CommonUtils;

/**
 * Created by liuguoquan on 2017/5/25.
 */

public class MoreFragment extends BaseFragment {

  @Override protected Status getCurrentStatus() {
    return Status.STATUS_NORMAL;
  }

  @Override protected int getContentView() {
    return R.layout.fragment_more;
  }

  @Override public BasePresenter initPresenter() {
    return null;
  }

  @Override protected String getPageTitle() {
    return "我的";
  }

  @Override protected void initData(Bundle savedInstanceState) {
    TranslucentStatusCompat.requestTranslucentStatus(getActivity());
  }

  @Override protected void initView(View parent) {
    getStatusBar().setBackgroundResource(R.color.main_color_pressed);
    getToolBar().setBackgroundResource(R.color.main_color_normal);
    TextView title = UIUtil.setCenterTitle(getToolBar(), getPageTitle());
    CommonUtils.updateTitleText(title);
  }

  @OnClick({R.id.search,R.id.starred})
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.search:
        Intent intent = new Intent(getActivity(),JavaActivity.class);
        ActivityUtil.startActivity(this,intent);
        break;
      case R.id.starred:
        break;
    }
  }
}
