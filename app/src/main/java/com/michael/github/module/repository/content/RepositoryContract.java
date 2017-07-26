package com.michael.github.module.repository.content;

import com.mdroid.PausedHandler;
import com.michael.github.base.AppBaseFragmentPresenter;
import com.michael.github.base.AppBaseView;
import com.michael.github.bean.RepositoryModel;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import java.util.List;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public interface RepositoryContract {

  interface IRepositoryView extends AppBaseView<IRepositoryPresenter> {

    void showSuccess(List<RepositoryModel.RepositoryItem> items);
    void showFailure(String msg);

  }

  abstract class IRepositoryPresenter extends AppBaseFragmentPresenter<IRepositoryView> {

    /**
     * @param provider 用于rxJava的生命周期管理,具体使用:在Observable.subscribe()前调用
     * Observable.compose(mProvider.<ApiResult>bindUntilEvent(FragmentEvent.DESTROY))<br/>
     * 可通过{@link BaseFragment#mLifecycleProvider}获取
     * @param handler 用于控制fragment onPause时暂停发布消息，可通过{@link BaseFragment#getHandler()}获取
     */
    public IRepositoryPresenter(LifecycleProvider<FragmentEvent> provider, PausedHandler handler) {
      super(provider, handler);
    }

    public abstract void searchRepository(SearchRepositoryCondition condition);
  }
}
