package com.michael.github.base;

import android.view.View;
import com.mdroid.lib.core.base.BaseActivity;
import com.mdroid.lib.core.base.BaseFragment;
import com.mdroid.lib.core.base.BaseView;
import com.mdroid.lib.core.eventbus.EventBusEvent;
import com.michael.github.R;
import com.michael.github.module.dialog.ProcessDialog;

/**
 * Description：
 */
public abstract class AppBaseActivity<V extends AppBaseView, T extends AppBaseActivityPresenter<V>>
    extends BaseActivity<V, T>
    implements BaseView<T>,EventBusEvent.INotify {
  private ProcessDialog mProcessDialog;
  /**
   * 数据等加载指示器，默认空实现
   *
   * @param isActive 是否正在处理
   */
  @Override public void setLoadingIndicator(boolean isActive) {
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mProcessDialog = null;
  }

  protected void showProcessDialog() {
    if (mProcessDialog == null) mProcessDialog = ProcessDialog.create(this);
    mProcessDialog.show();
  }

  protected void dismissProcessDialog() {
    if (mProcessDialog != null) {
      mProcessDialog.dismiss();
    }
  }

  @Override public void onNotify(EventBusEvent event) {

  }
}
