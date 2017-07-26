package com.michael.github.module.repository.content;

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
import com.michael.github.bean.RepositoryModel;
import java.util.List;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public class SearchRepositoryAdapter extends BaseRecyclerViewAdapter<RepositoryModel.RepositoryItem>
    implements
    DrawableDivider.DrawableProvider {

  private Fragment mFragment;

  public SearchRepositoryAdapter(@NonNull List<RepositoryModel.RepositoryItem> data) {
    super(R.layout.list_item_repository, data);
  }

  public SearchRepositoryAdapter(Fragment fragment, @NonNull List<RepositoryModel.RepositoryItem> data) {
    super(R.layout.list_item_repository, data);
    this.mFragment = fragment;
  }

  @Override protected void convert(BaseViewHolder holder, final RepositoryModel.RepositoryItem item) {
    ImageView icon = holder.getView(R.id.icon);
    ImageLoader.loadAsCircle(icon,R.mipmap.ic_launcher,item.owner.avatar_url);
    holder.setText(R.id.name,item.name);
    holder.setText(R.id.language,item.language);
    holder.setText(R.id.desc,item.description);
    String stars = "stars:"+item.stargazers_count+" " +"fork:"+item.forks_count;
    holder.setText(R.id.stars,stars);
    String[] time = item.updated_at.split("T");
    holder.setText(R.id.time,"Update at "+ time[0]);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mFragment == null) {
          return;
        }
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
    return AndroidUtils.dp2px(mContext,10.0f);
  }
}
