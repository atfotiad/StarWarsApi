package com.atfotiad.starwarsapi.model;

import android.app.Application;
import android.util.Log;

import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleDataSource extends PageKeyedDataSource<Integer, People> {
    ApiInterface apiInterface;
    Application application;
    MutableLiveData <String>networkState;


    public PeopleDataSource(ApiInterface apiInterface, Application application) {
        this.apiInterface = apiInterface;
        this.application = application;
        networkState = new MutableLiveData<>();

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, People> callback) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SwapiResponse> fetchPeople = apiInterface.getAllCharacters(1);

        fetchPeople.enqueue(new Callback<SwapiResponse>() {
            @Override
            public void onResponse(@NonNull Call<SwapiResponse> call,@NonNull Response<SwapiResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }

                SwapiResponse jsonResponse = response.body();

                assert jsonResponse != null;
                ArrayList<People> characters = (ArrayList<People>) jsonResponse.getPeople();

                callback.onResult(characters,null,2);
            }

            @Override
            public void onFailure(@NonNull Call<SwapiResponse> call, @NonNull Throwable t) {
                Log.e("From DataSource", "onFailure: " + t.getMessage());
                networkState.postValue(t.getMessage()+"\nPlease connect to the internet and swipe down to refresh");

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, People> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, People> callback) {

        Call<SwapiResponse> fetchPeople = apiInterface.getAllCharacters(params.key);

        fetchPeople.enqueue(new Callback<SwapiResponse>() {
            @Override
            public void onResponse(@NonNull Call<SwapiResponse> call, @NonNull Response<SwapiResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }

                SwapiResponse jsonResponse = response.body();

                assert jsonResponse != null;
                ArrayList<People>  characters = (ArrayList<People>) jsonResponse.getPeople();

                callback.onResult(characters,params.key+1);
            }

            @Override
            public void onFailure(@NonNull Call<SwapiResponse> call, @NonNull Throwable t) {
                Log.e("From DataSource", "onFailure: " + t.getMessage());
                networkState.postValue(t.getMessage() +"\nPlease connect to the internet and swipe down to refresh");

            }
        });

    }
    public MutableLiveData <String> getNetworkState(){
        return networkState;
    }

}
