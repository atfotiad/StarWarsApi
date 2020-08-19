package com.atfotiad.starwarsapi.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final  String Base_url =  "https://swapi.dev/api/";
    public static Retrofit retrofit = null;

    public static  Retrofit getClient(){
        if (retrofit == null){
            retrofit  =  new Retrofit.Builder()
                    .baseUrl(Base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
