package com.michael.github.network;

import com.michael.github.bean.RepositoryModel;
import com.michael.github.bean.UserModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public interface SearchApi {

  /**
   * https://api.github.com/search/repositories?q=language:java&sort=stars&order=desc&per_page=10&page=2
   */
  @GET("/search/repositories") Observable<RepositoryModel> search(@Query("q") String q,
      @Query("sort") String sort, @Query("order") String Order, @Query("page") int page,
      @Query("per_page") int limit);

  /**
   * https://api.github.com/search/users?q=language:go&sort=stars&order=desc&per_page=10&page=1
   * @param q
   * @param sort
   * @param Order
   * @param page
   * @param limit
   * @return
   */
  @GET("/search/users") Observable<UserModel> searchUsers(@Query("q") String q,
      @Query("sort") String sort, @Query("order") String Order, @Query("page") int page,
      @Query("per_page") int limit);
}
