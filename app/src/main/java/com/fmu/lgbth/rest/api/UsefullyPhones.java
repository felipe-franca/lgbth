package com.fmu.lgbth.rest.api;

import com.fmu.lgbth.model.UsefullyPhone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsefullyPhones {
    @GET("/usefully-phones")
    Call<List<UsefullyPhone>> get();
}
