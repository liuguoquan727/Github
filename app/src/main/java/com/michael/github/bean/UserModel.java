package com.michael.github.bean;

import java.util.List;

/**
 * Created by liuguoquan on 2017/5/26.
 */

public class UserModel extends BaseModel{

  public int total_count;
  public boolean incomplete_results;
  public List<UserItem> items;

  public static class UserItem extends BaseModel{

    public String login;
    public int id;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public boolean site_admin;
    public int score;
  }
}
