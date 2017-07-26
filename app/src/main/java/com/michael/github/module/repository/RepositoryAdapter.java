package com.michael.github.module.repository;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.michael.github.module.repository.content.GoFragment;
import com.michael.github.module.repository.content.JavaFragment;
import com.michael.github.module.repository.content.JavaScriptFragment;
import com.michael.github.module.repository.content.PythonFragment;
import java.util.List;

/**
 * Created by liuguoquan on 2017/5/25.
 */

public class RepositoryAdapter extends FragmentPagerAdapter {

  private List<String> mItems;

  public RepositoryAdapter(FragmentManager fm,List<String> items) {
    super(fm);
    this.mItems = items;
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new JavaFragment();
      case 1:
        return new PythonFragment();
      case 2:
        return new GoFragment();
      case 3:
        return new JavaScriptFragment();
    }
    return new JavaFragment();
  }

  @Override public int getCount() {
    return mItems == null ? 0 : mItems.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    return mItems.get(position);
  }
}
