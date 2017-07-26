package com.michael.github.module.user.content;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.mdroid.lib.core.utils.ImageLoader;
import com.mdroid.lib.recyclerview.BaseRecyclerViewAdapter;
import com.mdroid.lib.recyclerview.BaseViewHolder;
import com.mdroid.utils.AndroidUtils;
import com.mdroid.view.recyclerView.flexibledivider.DrawableDivider;
import com.michael.github.R;
import com.michael.github.bean.UserModel;
import java.util.List;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public class SearchUsersAdapter extends BaseRecyclerViewAdapter<UserModel.UserItem> implements
    DrawableDivider.DrawableProvider {

  private Fragment mFragment;

  public SearchUsersAdapter(Fragment fragment, @NonNull List<UserModel.UserItem> data) {
    super(R.layout.list_item_users, data);
    this.mFragment = fragment;
  }

  @Override protected void convert(BaseViewHolder holder, final UserModel.UserItem item) {
    ImageView icon = holder.getView(R.id.icon);
    ImageLoader.loadAsCircle(icon,R.mipmap.ic_launcher,item.avatar_url);
    holder.setText(R.id.name,item.login);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(item.html_url);
        intent.setData(content_url);
        mFragment.startActivity(intent);
      }
    });
  }

  @Override public Drawable dividerDrawable(int i, RecyclerView recyclerView) {
    return null;
  }

  @Override public int dividerSize(int i, RecyclerView recyclerView) {
    return AndroidUtils.dp2px(mFragment.getActivity(),10.0f);
  }
}
