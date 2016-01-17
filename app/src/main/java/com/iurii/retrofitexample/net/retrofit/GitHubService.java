package com.iurii.retrofitexample.net.retrofit;

import com.iurii.retrofitexample.net.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface GitHubService {

    @GET("/users")
    void users(Callback<List<User>> cb);

}

