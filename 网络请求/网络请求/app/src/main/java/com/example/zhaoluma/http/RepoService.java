package com.example.zhaoluma.http;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhaoluma on 2017/12/15.
 */

public interface RepoService {
    @GET("/users/{user}/repos")
    Observable<List<Repositories>> getUser(@Path("user") String user);
}
