package com.fmu.lgbth.rest.api;

import com.fmu.lgbth.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostsApi {
    @GET("/posts/posts-list")
    Call<List<Post>> getAll();

    @GET("/posts/news")
    Call<List<Post>> getNews();
}
