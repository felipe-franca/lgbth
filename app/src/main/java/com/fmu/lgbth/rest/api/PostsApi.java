package com.fmu.lgbth.rest.api;

import com.fmu.lgbth.model.Post;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostsApi {
    @GET("/posts/posts-list")
    Call<List<Post>> getAll();

    @GET("/posts/news")
    Call<List<Post>> getNews();

    @GET("/favorites/posts/{userId}")
    Call<List<Post>> getFavoritePosts(@Path("userId") int userId);

    @PUT("/favorites/posts/create")
    Call<ResponseBody> postFavorite(@Body JsonObject json);

    @GET("/posts/{postId}")
    Call<Post> getPostById(@Path("postId") int postId);

    @HTTP(method = "DELETE", path = "/favorites/posts/remove", hasBody = true)
    Call<ResponseBody> unfavoritePost(@Body JsonObject json);

    @GET("/posts/category/{category}")
    Call<List<Post>> getPostByCategory(@Path("category") String category);
}
