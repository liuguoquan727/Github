package com.michael.github.module.user;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.michael.github.module.user.content.GoUserFragment;
import com.michael.github.module.user.content.JavaScriptUserFragment;
import com.michael.github.module.user.content.JavaUserFragment;
import com.michael.github.module.user.content.PythonUserFragment;
import java.util.List;

/**
 * Created by liuguoquan on 2017/5/25.
 */

public class UserAdapter extends FragmentPagerAdapter {

  private List<String> mItems;

  public UserAdapter(FragmentManager fm,List<String> items) {
    super(fm);
    this.mItems = items;
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new JavaUserFragment();
      case 1:
        return new PythonUserFragment();
      case 2:
        return new GoUserFragment();
      case 3:
        return new JavaScriptUserFragment();
    }
    return new JavaUserFragment();
  }

  @Override public int getCount() {
    return mItems == null ? 0 : mItems.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    return mItems.get(position);
  }
}
