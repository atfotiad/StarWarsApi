package com.atfotiad.starwarsapi.repository;

import android.app.Application;
import android.util.Log;

import com.atfotiad.starwarsapi.adapters.PeopleAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityMainBinding;
import com.atfotiad.starwarsapi.model.Result;
import com.atfotiad.starwarsapi.model.SwapiResponse;
import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository{
    private ArrayList<Result> allCharacters = null;
    private ArrayList<Result> nextPage = null;
    private Retrofit apiService;
    private Application application;


    public Repository(Application application){
        this.application = application;
        apiService = ApiClient.getClient();


    }
    public void getAllCharacters(PeopleAdapter peopleAdapter, ActivityMainBinding binding, int page){
        ApiInterface request =apiService.create(ApiInterface.class);
        Call<SwapiResponse> fetchPeople = request.getAllCharacters(page);
        fetchPeople.enqueue(new Callback<SwapiResponse>() {
            @Override
            public void onResponse(Call<SwapiResponse> call, Response<SwapiResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }
                SwapiResponse jsonResponse = response.body();
                assert jsonResponse != null;
                allCharacters = (ArrayList<Result>) jsonResponse.getResults();
                for (int i=0 ; i<allCharacters.size();i++){
                    Log.d("Repo", "onResponse: "+ jsonResponse.getResults().get(i).getName());
                }
               // peopleAdapter.addCharacters(allCharacters);
                Log.d("Item count", "onResponse: "+peopleAdapter.getItemCount());


            }

            @Override
            public void onFailure(Call<SwapiResponse> call, Throwable t) {
                Log.e("From Repo", "onFailure: " + t.getMessage());

            }
        });

        /*

        //page++;
        Call <SwapiResponse> fetchSecondPage = request.getAllCharacters(2);
        fetchSecondPage.enqueue(new Callback<SwapiResponse>() {
            @Override
            public void onResponse(Call<SwapiResponse> call, Response<SwapiResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }
                SwapiResponse jsonResponse = response.body();
                assert jsonResponse != null;
                nextPage = (ArrayList<Result>) jsonResponse.getResults();
                for (int i=0 ; i<nextPage.size();i++){
                    Log.d("Repo", "onResponse: "+ jsonResponse.getResults().get(i).getName());
                }
                peopleAdapter.addCharacters(nextPage);
                Log.d("Item count", "onResponse: "+peopleAdapter.getItemCount());

            }

            @Override
            public void onFailure(Call<SwapiResponse> call, Throwable t) {
                Log.e("From Repo", "onFailure: " + t.getMessage());

            }
        });

*/
    }

}
