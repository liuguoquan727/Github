package com.michael.github;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import com.mdroid.app.TabManager;
import com.mdroid.lib.core.base.BaseFragment;
import com.mdroid.lib.core.base.BasePresenter;
import com.mdroid.lib.core.base.Status;
import com.mdroid.lib.core.utils.Toost;
import com.michael.github.base.AppBaseActivity;
import com.michael.github.module.more.MoreFragment;
import com.michael.github.module.repository.RepositoryFragment;
import com.michael.github.module.user.UserFragment;

public class MainActivity extends AppBaseActivity {

  public static final String REPOSITORY = "repository";
  public static final String USER = "user";
  public static final String MORE = "more";

  private TabManager mTabManager;
  private long mBackTime;
  @BindView(R.id.navigation) BottomNavigationView mBottomNavigationView;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_home:
              if (!REPOSITORY.equals(mTabManager.getCurrentTag())) {
                mTabManager.changeTab(REPOSITORY);
              }
              return true;
            case R.id.navigation_dashboard:
              if (!USER.equals(mTabManager.getCurrentTag())) {
                mTabManager.changeTab(USER);
              }
              return true;
            case R.id.navigation_notifications:
              if (!MORE.equals(mTabManager.getCurrentTag())) {
                mTabManager.changeTab(MORE);
              }
              return true;
          }
          return false;
        }
      };

  @Override public Status getCurrentStatus() {
    return null;
  }

  @Override public String getPageTitle() {
    return null;
  }

  @Override public int getLayoutResId() {
    return R.layout.activity_main;
  }

  @Override public BasePresenter initPresenter() {
    return null;
  }


  @Override public void initView(Bundle savedInstanceState) {
    //mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
    mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    mTabManager = new TabManager(this, getSupportFragmentManager(), R.id.main_container);
    mTabManager.addTab(REPOSITORY, RepositoryFragment.class, null)
        .addTab(USER, UserFragment.class, null)
        .addTab(MORE, MoreFragment.class, null);
    if (savedInstanceState != null) {
      mTabManager.restoreState(savedInstanceState);
    } else {
      mTabManager.changeTab(REPOSITORY);
    }
  }

  @Override public void onBackPressed() {
    BaseFragment fragment = (BaseFragment) mTabManager.getCurrentFragment();
    if (fragment != null && fragment.onBackPressed()) {
      return;
    }
    long time = SystemClock.elapsedRealtime();
    if (time - mBackTime > 2000) {
      Toost.message("再按一次退出应用");
      mBackTime = time;
      return;
    }
    super.onBackPressed();
  }

  @Override public void executeCloseAnim() {
  }

  public View getFooter() {
    return mBottomNavigationView;
  }
}
