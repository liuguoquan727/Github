package com.michael.github.module.more;

import com.mdroid.PausedHandler;
import com.mdroid.lib.core.base.BaseActivity;
import com.michael.github.base.AppBaseActivityPresenter;
import com.michael.github.base.AppBaseView;
import com.michael.github.bean.RepositoryModel;
import com.michael.github.module.repository.content.SearchRepositoryCondition;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import java.util.List;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public interface JavaContract {

  interface IRepositoryView extends AppBaseView<IRepositoryPresenter> {

    void showSuccess(List<RepositoryModel.RepositoryItem> items);
    void showFailure(String msg);

  }

  abstract class IRepositoryPresenter extends AppBaseActivityPresenter<IRepositoryView> {

    /**
     * @param provider 用于rxJava的生命周期管理,具体使用:在Observable.subscribe()前调用
     * Observable.compose(mProvider.<ApiResult>bindUntilEvent(FragmentEvent.DESTROY))<br/>
     * 可通过{@link BaseActivity#mLifecycleProvider}获取
     * @param handler 用于控制fragment onPause时暂停发布消息，可通过{@link BaseActivity#getHandler()}获取
     */
    public IRepositoryPresenter(LifecycleProvider<ActivityEvent> provider, PausedHandler handler) {
      super(provider, handler);
    }

    public abstract void searchRepository(SearchRepositoryCondition condition);
  }
}
