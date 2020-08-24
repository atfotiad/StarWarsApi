package com.atfotiad.starwarsapi.model;

import android.app.Application;

import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PeopleDataSourceFactory extends DataSource.Factory<Integer, People>{

    private ApiInterface apiInterface;
    private Application application;
    private MutableLiveData<PeopleDataSource> mutableLiveData;

    public PeopleDataSourceFactory(ApiInterface apiInterface, Application application) {
        this.apiInterface = apiInterface;
        this.application = application;
        mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, People> create() {

        PeopleDataSource peopleDataSource = new PeopleDataSource(apiInterface, application);
        mutableLiveData.postValue(peopleDataSource);
        return peopleDataSource;
    }

    public MutableLiveData<PeopleDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
