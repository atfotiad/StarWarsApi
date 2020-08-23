package com.atfotiad.starwarsapi.model;

import android.app.Application;
import android.util.Log;

import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleDataSource extends PageKeyedDataSource<Integer,Result> {
    ApiInterface apiInterface;
    Application application;

    public PeopleDataSource(ApiInterface apiInterface, Application application) {
        this.apiInterface = apiInterface;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Result> callback) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SwapiResponse> fetchPeople = apiInterface.getAllCharacters(1);

        fetchPeople.enqueue(new Callback<SwapiResponse>() {
            @Override
            public void onResponse(Call<SwapiResponse> call, Response<SwapiResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }

                SwapiResponse jsonResponse = response.body();

                assert jsonResponse != null;
                ArrayList<Result> characters = (ArrayList<Result>) jsonResponse.getResults();

                callback.onResult(characters,null,2);
            }

            @Override
            public void onFailure(Call<SwapiResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {

        Call<SwapiResponse> fetchPeople = apiInterface.getAllCharacters(params.key);

        fetchPeople.enqueue(new Callback<SwapiResponse>() {
            @Override
            public void onResponse(Call<SwapiResponse> call, Response<SwapiResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }

                SwapiResponse jsonResponse = response.body();

                assert jsonResponse != null;
                ArrayList<Result>  characters = (ArrayList<Result>) jsonResponse.getResults();

                callback.onResult(characters,params.key+1);
            }

            @Override
            public void onFailure(Call<SwapiResponse> call, Throwable t) {

            }
        });

    }
}
