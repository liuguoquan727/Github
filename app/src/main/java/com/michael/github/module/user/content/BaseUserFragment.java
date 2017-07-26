package com.michael.github.module.user.content;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.BindView;
import com.mdroid.lib.core.base.Status;
import com.mdroid.lib.core.utils.Toost;
import com.mdroid.lib.recyclerview.BaseRecyclerViewAdapter;
import com.mdroid.utils.AndroidUtils;
import com.mdroid.view.recyclerView.flexibledivider.DrawableDivider;
import com.michael.github.MainActivity;
import com.michael.github.R;
import com.michael.github.base.LazyLoadFragment;
import com.michael.github.bean.UserModel;
import com.michael.github.module.repository.QuickReturnOnScrollListener;
import com.michael.github.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguoquan on 2017/5/25.
 */

public abstract class BaseUserFragment extends
    LazyLoadFragment<UserContract.IUserView, UserContract.IUserPresenter> implements
    UserContract.IUserView, BaseRecyclerViewAdapter.OnLoadingMoreListener {

  @BindView(R.id.refresh_layout) SwipeRefreshLayout mRefreshLayout;
  @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
  private SearchUsersAdapter mAdapter;
  private SearchUserCondition mCondition = new SearchUserCondition();
  private boolean mIsDataLoaded = false;
  List<UserModel.UserItem> mItems = new ArrayList<>();

  public abstract String getType();

  @Override protected int getContentView() {
    return R.layout.fragment_content_repository;
  }

  @Override public UserContract.IUserPresenter initPresenter() {
    return new UserPresenter(mLifecycleProvider,getHandler());
  }

  @Override protected String getPageTitle() {
    return null;
  }

  @Override protected void initData(Bundle savedInstanceState) {

  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
  }

  @Override protected void initView(View parent) {
    super.initView(parent);
    View footer = ((MainActivity) getActivity()).getFooter();
    int height = footer.getHeight();
    if (height == 0) {
      AndroidUtils.prepareView(footer);
      height = footer.getMeasuredHeight();
    }
    QuickReturnOnScrollListener onScrollListener = new QuickReturnOnScrollListener();
    onScrollListener.addFooterItem(
        new QuickReturnOnScrollListener.Item(footer, 0, 2, height, 300));
    mRecyclerView.addOnScrollListener(onScrollListener);
    mRefreshLayout.setColorSchemeResources(R.color.main_color_normal);
    mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        refresh();
      }
    });
    mAdapter = new SearchUsersAdapter(this,mItems);
    mAdapter.setHasMore(mItems.size() >= 10);
    mAdapter.setOnLoadingMoreListener(this);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.addItemDecoration(new DrawableDivider(mAdapter));
    mRecyclerView.setAdapter(mAdapter);
    CommonUtils.setRecyclerViewLoadMore(getActivity(),mAdapter,mRecyclerView);
    View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.view_empty,mRecyclerView,false);
    emptyView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        refresh();
      }
    });
    mAdapter.setEmptyView(emptyView);
  }

  @Override protected void lazyInitView(View parent) {
    if (!mIsDataLoaded) {
      mIsDataLoaded = true;
      refresh();
    }
  }

  private void refresh() {
    mCondition.q = getType();
    mCondition.sort = "stars";
    mCondition.order = "desc";
    mCondition.page = 1;
    mCondition.limit = 10;
    mPresenter.searchUser(mCondition);
  }

  @Override public void showSuccess(List<UserModel.UserItem> items) {
    switchStatus(Status.STATUS_NORMAL);
    stopRefresh();
    if (mAdapter.isLoadingMore()) {
      mAdapter.loadMoreFinish(items.size() == mCondition.limit,items);
    } else {
      mAdapter.setHasMore(items.size() == mCondition.limit);
      mAdapter.resetData(items);
    }

  }


  @Override public void showFailure(String msg) {
    Toost.message(msg);
    stopRefresh();
    if (mAdapter.isLoadingMore()) {
      switchStatus(Status.STATUS_NORMAL);
      mAdapter.loadMoreFailure();
    } else {
      if (mItems.size() > 0) {
        switchStatus(Status.STATUS_NORMAL);
      } else {
        switchStatus(Status.STATUS_ERROR);
        mErrorView.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            refresh();
          }
        });
      }
    }
  }

  private void stopRefresh() {
    if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
      mRefreshLayout.setRefreshing(false);
    }
  }

  @Override public void requestMoreData() {
    mCondition.page++;
    mPresenter.searchUser(mCondition);
  }
}
