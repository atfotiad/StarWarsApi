package com.atfotiad.starwarsapi.repository;

import android.app.Application;
import android.util.Log;

import com.atfotiad.starwarsapi.model.People;
import com.atfotiad.starwarsapi.model.PeopleDataSource;
import com.atfotiad.starwarsapi.model.PeopleDataSourceFactory;
import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class Repository{


    LiveData<PeopleDataSource> peopleDataSourceLiveData;
    private LiveData<PagedList<People>> peoplePagedList;


    public Repository(Application application){

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        PeopleDataSourceFactory peopleDataSourceFactory = new PeopleDataSourceFactory(apiInterface,application);
        peopleDataSourceLiveData = peopleDataSourceFactory.getMutableLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .setPageSize(10)
                .setPrefetchDistance(50)
                .build();

        Executor executor = Executors.newFixedThreadPool(4);

        peoplePagedList = new LivePagedListBuilder<>(peopleDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build();

    }

    public LiveData<PagedList<People>> getPeoplePagedList() {
        return peoplePagedList;
    }

    public List<String> getFilmNames(List<String> films){
        ArrayList<String> newFilms = new ArrayList<>();
        for (String film :films ){
            String name = getNameFromUrl(film);
            Log.d("from names", "getFilmNameFromUrl: "+ name);
            newFilms.add(name);

        }
        return newFilms;
    }

    public String getNameFromUrl(String film){
        char filmId = film.charAt(film.length() - 2);

        switch (filmId){
            case '1':
                return  "Star Wars Episode IV A New Hope";
            case '2':
                return  "Star Wars Episode V The Empire Strikes Back";
            case '3':
                return  "Star Wars Episode VI Return of the Jedi";
            case '4':
                return  "Star Wars Episode I The Phantom Menace";
            case '5':
                return "Star Wars Episode II Attack of the Clones";
            case '6':
                return "Star Wars Episode III Revenge of the Sith";
            default:
                return null;
        }

    }

}
