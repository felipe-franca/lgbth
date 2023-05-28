package com.fmu.lgbth.rest.api;

import com.fmu.lgbth.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {
    @POST("/user/create")
    Call<User> create(@Body User user);

    @GET("/user/get?")
    Call<User> get(@Query("id") int id);

    @PUT("/user/update")
    Call<User> update(@Body User user);

    @POST("/user/sign-in")
    Call<User> signIn(@Body User user);
}
