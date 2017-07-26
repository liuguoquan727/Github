package com.michael.github.module.repository.content;

import com.mdroid.PausedHandler;
import com.mdroid.lib.core.base.BaseFragment;
import com.mdroid.lib.core.rxjava.PausedHandlerScheduler;
import com.michael.github.bean.RepositoryModel;
import com.michael.github.module.repository.content.RepositoryContract.IRepositoryPresenter;
import com.michael.github.network.Api;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public class RepositoryPresenter extends IRepositoryPresenter {
  /**
   * @param provider 用于rxJava的生命周期管理,具体使用:在Observable.subscribe()前调用
   * Observable.compose(mProvider.<ApiResult>bindUntilEvent(FragmentEvent.DESTROY))<br/>
   * 可通过{@link BaseFragment#mLifecycleProvider}获取
   * @param handler 用于控制fragment onPause时暂停发布消息，可通过{@link BaseFragment#getHandler()}获取
   */
  public RepositoryPresenter(LifecycleProvider<FragmentEvent> provider, PausedHandler handler) {
    super(provider, handler);
  }

  @Override public void searchRepository(SearchRepositoryCondition condition) {
    Api.getSearchApi()
        .search(condition.q, condition.sort, condition.order, condition.page, condition.limit)
        .subscribeOn(Schedulers.io())
        .observeOn(PausedHandlerScheduler.from(mHandler))
        .compose(mProvider.<RepositoryModel>bindUntilEvent(FragmentEvent.DESTROY))
        .subscribe(new Consumer<RepositoryModel>() {
          @Override public void accept(RepositoryModel repositoryModel) {
            mView.showSuccess(repositoryModel.items);
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) {
            mView.showFailure(throwable.getMessage());
          }
        });
  }

  @Override protected void destroy() {

  }
}
