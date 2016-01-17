package com.iurii.retrofitexample.net.model;

import com.google.gson.annotations.SerializedName;

public class User {

    public String login;
    public int id;
    @SerializedName(value = "avatar_url")
    public String avatarUrl;
    @SerializedName(value = "html_url")
    public String htmlUrl;

    public User() {
    }
    public User(String login, int id, String avatarUrl, String htmlUrl) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
    }
}
