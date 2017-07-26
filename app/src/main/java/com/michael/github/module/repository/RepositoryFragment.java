package com.michael.github.module.repository;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.mdroid.app.TranslucentStatusCompat;
import com.mdroid.lib.core.base.BaseFragment;
import com.mdroid.lib.core.base.BasePresenter;
import com.mdroid.lib.core.base.Status;
import com.mdroid.lib.core.utils.UIUtil;
import com.michael.github.R;
import com.michael.github.utils.CommonUtils;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuguoquan on 2017/5/25.
 */

public class RepositoryFragment extends BaseFragment {

  @BindView(R.id.tablayout) TabLayout mTablayout;
  @BindView(R.id.viewpager) ViewPager mViewpager;
  @BindView(R.id.tool_bar) Toolbar mToolbar;
  @BindView(R.id.bar_layout) AppBarLayout mAppBarLayout;

  @Override protected Status getCurrentStatus() {
    return Status.STATUS_NORMAL;
  }

  @Override protected int getContentView() {
    return R.layout.fragment_repository;
  }

  @Override public BasePresenter initPresenter() {
    return null;
  }

  @Override protected String getPageTitle() {
    return "仓库";
  }

  @Override protected void initData(Bundle savedInstanceState) {
    TranslucentStatusCompat.requestTranslucentStatus(getActivity());
  }

  @Override protected void initView(View parent) {
    UIUtil.requestStatusBarLight(this, true);
    getStatusBar().setBackgroundResource(R.color.main_color_pressed);
    mContentContainer.setPadding(0, getStatusBarHeight(), 0, 0);
    getToolBarContainer().setVisibility(View.GONE);
    TextView title = UIUtil.setCenterTitle(mToolbar, getPageTitle());
    CommonUtils.updateTitleText(title);
    List<String> titles = Arrays.asList(getResources().getStringArray(R.array.category));
    mViewpager.setAdapter(new RepositoryAdapter(getChildFragmentManager(),titles));
    mTablayout.setupWithViewPager(mViewpager);
  }

}
