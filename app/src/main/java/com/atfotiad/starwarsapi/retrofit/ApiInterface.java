package com.atfotiad.starwarsapi.retrofit;

import com.atfotiad.starwarsapi.model.SwapiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("people")
    Call<SwapiResponse> getAllCharacters(@Query("page") int page);

}
