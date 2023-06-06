package com.fmu.lgbth.rest.api;

import com.fmu.lgbth.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostsApi {
    @GET("/posts/posts-list")
    Call<List<Post>> getAll();

    @GET("/posts/news")
    Call<List<Post>> getNews();

    @GET("/favorites/posts/{userId}")
    Call<List<Post>> getFavoritePosts(@Path("userId") int userId);
}
