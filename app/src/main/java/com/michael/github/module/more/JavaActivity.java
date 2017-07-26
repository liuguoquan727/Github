package com.michael.github.module.more;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.BindView;
import com.jaeger.library.StatusBarUtil;
import com.mdroid.lib.core.base.BaseActivity;
import com.mdroid.lib.core.base.Status;
import com.mdroid.lib.core.view.StateFrameLayout;
import com.mdroid.lib.recyclerview.BaseRecyclerViewAdapter;
import com.mdroid.view.recyclerView.flexibledivider.DrawableDivider;
import com.michael.github.R;
import com.michael.github.base.AppBaseActivity;
import com.michael.github.bean.RepositoryModel;
import com.michael.github.module.repository.content.SearchRepositoryAdapter;
import com.michael.github.module.repository.content.SearchRepositoryCondition;
import com.michael.github.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguoquan on 2017/7/26.
 */

public class JavaActivity extends AppBaseActivity<JavaContract.IRepositoryView, JavaContract.IRepositoryPresenter>
    implements JavaContract.IRepositoryView, BaseRecyclerViewAdapter.OnLoadingMoreListener {

  @BindView(R.id.refresh_layout) SwipeRefreshLayout mRefreshLayout;
  @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
  @BindView(R.id.state_layout) StateFrameLayout mStateFrameLayout;
  private SearchRepositoryAdapter mAdapter;
  private SearchRepositoryCondition mCondition = new SearchRepositoryCondition();
  List<RepositoryModel.RepositoryItem> mItems = new ArrayList<>();

  @Override public Status getCurrentStatus() {
    return Status.STATUS_LOADING;
  }

  @Override public String getPageTitle() {
    return null;
  }

  @Override public int getLayoutResId() {
    return R.layout.activity_java_repository;
  }

  @Override public void initView(Bundle savedInstanceState) {
    StatusBarUtil.setColor(this,getResources().getColor(R.color.main_color_pressed),0);
    mAdapter = new SearchRepositoryAdapter(mItems);
    mAdapter.setHasMore(mItems.size() >= 10);
    mAdapter.setOnLoadingMoreListener(this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.addItemDecoration(new DrawableDivider(mAdapter));
    mRecyclerView.setAdapter(mAdapter);
    CommonUtils.setRecyclerViewLoadMore(this,mAdapter,mRecyclerView);
    View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty,mRecyclerView,false);
    emptyView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        refresh();
      }
    });
    mAdapter.setEmptyView(emptyView);
    mStateFrameLayout.switchStatus(getCurrentStatus());
    refresh();
  }

  @Override public JavaContract.IRepositoryPresenter initPresenter() {
    return new JavaPresenter(mLifecycleProvider,getHandler());
  }

  private void refresh() {
    mCondition.q = "language:java";
    mCondition.sort = "stars";
    mCondition.order = "desc";
    mCondition.page = 1;
    mCondition.limit = 10;
    mPresenter.searchRepository(mCondition);
  }

  @Override public void showSuccess(List<RepositoryModel.RepositoryItem> items) {
    mStateFrameLayout.switchStatus(Status.STATUS_NORMAL);
    if (mAdapter.isLoadingMore()) {
      mAdapter.loadMoreFinish(items.size() == mCondition.limit,items);
    } else {
      mAdapter.setHasMore(items.size() == mCondition.limit);
      mAdapter.resetData(items);
    }
  }

  @Override public void showFailure(String msg) {
    if (mAdapter.isLoadingMore()) {
      mStateFrameLayout.switchStatus(Status.STATUS_NORMAL);
      mAdapter.loadMoreFailure();
    } else {
      if (mItems.size() > 0) {
        mStateFrameLayout.switchStatus(Status.STATUS_NORMAL);
      } else {
        mStateFrameLayout.switchStatus(Status.STATUS_ERROR);
      }
    }
  }

  @Override public void setLoadingIndicator(boolean isActive) {

  }

  @Override public void requestMoreData() {
    mCondition.page++;
    mPresenter.searchRepository(mCondition);
  }

  private void stopRefresh() {
    if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
      mRefreshLayout.setRefreshing(false);
    }
  }
}
