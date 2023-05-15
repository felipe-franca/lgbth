package com.fmu.lgbth.rest;

import com.fmu.lgbth.model.User;
import com.fmu.lgbth.rest.api.UserApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private final UserApi userApi;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.108:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userApi = retrofit.create(UserApi.class);
    }

    public UserApi getUserApi() { return userApi; }
}
