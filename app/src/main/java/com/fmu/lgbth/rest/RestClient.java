package com.fmu.lgbth.rest;

import com.fmu.lgbth.model.User;
import com.fmu.lgbth.rest.api.PostsApi;
import com.fmu.lgbth.rest.api.UsefullyPhones;
import com.fmu.lgbth.rest.api.UserApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private final UserApi userApi;
    private final UsefullyPhones usefullyPhonesApi;
    private final PostsApi postsApi;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://860c-200-170-249-117.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userApi = retrofit.create(UserApi.class);
        usefullyPhonesApi = retrofit.create(UsefullyPhones.class);
        postsApi = retrofit.create(PostsApi.class);
    }

    public UserApi getUserApi() { return userApi; }

    public UsefullyPhones getUsefullyPhonesApi() { return usefullyPhonesApi; }

    public PostsApi getPostApi() { return postsApi; }
}
