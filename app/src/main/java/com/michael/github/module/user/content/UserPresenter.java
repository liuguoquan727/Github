package com.michael.github.module.user.content;

import com.mdroid.PausedHandler;
import com.mdroid.lib.core.rxjava.PausedHandlerScheduler;
import com.michael.github.bean.UserModel;
import com.michael.github.network.Api;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public class UserPresenter extends UserContract.IUserPresenter {
  /**
   * @param provider 用于rxJava的生命周期管理,具体使用:在Observable.subscribe()前调用
   * Observable.compose(mProvider.<ApiResult>bindUntilEvent(FragmentEvent.DESTROY))<br/>
   */
  public UserPresenter(LifecycleProvider<FragmentEvent> provider, PausedHandler handler) {
    super(provider, handler);
  }

  @Override public void searchUser(SearchUserCondition condition) {
    Api.getSearchApi()
        .searchUsers(condition.q, condition.sort, condition.order, condition.page, condition.limit)
        .subscribeOn(Schedulers.io())
        .observeOn(PausedHandlerScheduler.from(mHandler))
        .compose(mProvider.<UserModel>bindUntilEvent(FragmentEvent.DESTROY))
        .subscribe(new Consumer<UserModel>() {
          @Override public void accept(UserModel model) {
            mView.showSuccess(model.items);
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
