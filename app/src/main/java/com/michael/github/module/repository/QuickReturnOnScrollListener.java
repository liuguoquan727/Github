package com.michael.github.module.repository;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听RecyclerView
 * Quick return likes Google plus
 */
public class QuickReturnOnScrollListener extends RecyclerView.OnScrollListener {

  // region Member Variables
  private List<Item> mHeaderViews;
  private List<Item> mFooterViews;
  private int mPrevScrollY = 0;
  // endregion

  public void addHeaderItem(Item item) {
    if (mHeaderViews == null) {
      mHeaderViews = new ArrayList<>();
    }
    mHeaderViews.add(item);
  }

  public void addFooterItem(Item item) {
    if (mFooterViews == null) {
      mFooterViews = new ArrayList<>();
    }
    mFooterViews.add(item);
  }

  private int getOrientation(RecyclerView parent) {
    LinearLayoutManager layoutManager;
    try {
      layoutManager = (LinearLayoutManager) parent.getLayoutManager();
    } catch (ClassCastException e) {
      throw new IllegalStateException(
          "DividerDecoration can only be used with a " + "LinearLayoutManager.", e);
    }
    return layoutManager.getOrientation();
  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    int diff;
    int direction = getOrientation(recyclerView);
    if (direction == LinearLayoutManager.VERTICAL) {
      diff = dy;
    } else {
      diff = dx;
    }
    mPrevScrollY += diff;
    if (mPrevScrollY < 0) {
      mPrevScrollY = 0;
    }
    int scrollY = mPrevScrollY;

    if (diff > 0) { // scrolling up
      if (mHeaderViews != null) {
        for (Item item : mHeaderViews) {
          View view = item.getView();
          if (diff > item.mScrollThreshold && scrollY > item.mMinTranslation) {
            if (item.mUpAnimator == null || !item.mUpAnimator.isStarted()) {
              if (item.mDownAnimator != null) {
                item.mDownAnimator.cancel();
              }
              item.mUpAnimator =
                  ObjectAnimator.ofFloat(view, "translationY", ViewHelper.getTranslationY(view),
                      -item.mMinTranslation);
              item.mUpAnimator.setDuration(item.mAnimationDuration).start();
            }
          }
        }
      }

      if (mFooterViews != null) {
        for (Item item : mFooterViews) {
          View view = item.getView();
          if (diff > item.mScrollThreshold) {
            if (item.mUpAnimator == null || !item.mUpAnimator.isStarted()) {
              if (item.mDownAnimator != null) {
                item.mDownAnimator.cancel();
              }
              item.mUpAnimator =
                  ObjectAnimator.ofFloat(view, "translationY", ViewHelper.getTranslationY(view),
                      item.mDistance);
              item.mUpAnimator.setDuration(item.mAnimationDuration).start();
            }
          }
        }
      }
    } else if (diff < 0) { // scrolling down
      if (mHeaderViews != null) {
        for (Item item : mHeaderViews) {
          View view = item.getView();
          if (diff < -item.mScrollThreshold) {
            if (item.mDownAnimator == null || !item.mDownAnimator.isStarted()) {
              if (item.mUpAnimator != null) {
                item.mUpAnimator.cancel();
              }
              item.mDownAnimator =
                  ObjectAnimator.ofFloat(view, "translationY", ViewHelper.getTranslationY(view), 0);
              item.mDownAnimator.setDuration(item.mAnimationDuration).start();
            }
          }
        }
      }

      if (mFooterViews != null) {
        for (Item item : mFooterViews) {
          View view = item.getView();
          if (diff < -item.mScrollThreshold) {
            if (item.mDownAnimator == null || !item.mDownAnimator.isStarted()) {
              if (item.mUpAnimator != null) {
                item.mUpAnimator.cancel();
              }
              item.mDownAnimator =
                  ObjectAnimator.ofFloat(view, "translationY", ViewHelper.getTranslationY(view), 0);
              item.mDownAnimator.setDuration(item.mAnimationDuration).start();
            }
          }
        }
      }
    }
  }

  public static class Item {
    private static final int DEFAULT_ANIMATION_DURATION = 150;
    private View mView;
    private int mMinTranslation;
    private int mScrollThreshold;
    private int mAnimationDuration;
    private ValueAnimator mUpAnimator;
    private ValueAnimator mDownAnimator;
    private int mDistance;

    public Item(View view, int minTranslation, int scrollThreshold) {
      this(view, minTranslation, scrollThreshold, 0, DEFAULT_ANIMATION_DURATION);
    }

    public Item(View view, int minTranslation, int scrollThreshold, int distance,
        int animationDuration) {
      this.mView = view;
      this.mMinTranslation = minTranslation;
      this.mScrollThreshold = scrollThreshold;
      this.mAnimationDuration = animationDuration;
      this.mDistance = distance;
    }

    public View getView() {
      return mView;
    }

    public void setView(View view) {
      this.mView = view;
    }

    public int getMinTranslation() {
      return mMinTranslation;
    }

    public void setMinTranslation(int minTranslation) {
      this.mMinTranslation = minTranslation;
    }

    public int getScrollThreshold() {
      return mScrollThreshold;
    }

    public void setScrollThreshold(int scrollThreshold) {
      this.mScrollThreshold = scrollThreshold;
    }

    public int getAnimationDuration() {
      return mAnimationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
      this.mAnimationDuration = animationDuration;
    }
  }
}
