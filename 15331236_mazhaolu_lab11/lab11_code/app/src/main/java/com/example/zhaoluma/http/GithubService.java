package com.example.zhaoluma.http;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhaoluma on 2017/12/14.
 */

public interface GithubService {
    @GET("/users/{user}")
    Observable<Github> getUser(@Path("user") String user);
}
