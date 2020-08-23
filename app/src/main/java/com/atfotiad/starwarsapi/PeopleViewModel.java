package com.atfotiad.starwarsapi;

import android.app.Application;

import com.atfotiad.starwarsapi.adapters.PeopleAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityMainBinding;
import com.atfotiad.starwarsapi.model.PeopleDataSource;
import com.atfotiad.starwarsapi.model.PeopleDataSourceFactory;
import com.atfotiad.starwarsapi.model.Result;
import com.atfotiad.starwarsapi.repository.Repository;
import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class PeopleViewModel extends AndroidViewModel {

    private Repository repository;
    LiveData<PeopleDataSource> peopleDataSourceLiveData;
    private LiveData<PagedList<Result>> peoplePagedList;

    public PeopleViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        PeopleDataSourceFactory peopleDataSourceFactory = new PeopleDataSourceFactory(apiInterface,application);
        peopleDataSourceLiveData = peopleDataSourceFactory.getMutableLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .setPrefetchDistance(30)
                .build();

        Executor executor = Executors.newFixedThreadPool(4);

        peoplePagedList = new LivePagedListBuilder<Integer,Result>(peopleDataSourceFactory,config)
                .setFetchExecutor(executor)
                .build();

    }
    public void getAllCharacters(PeopleAdapter adapter, ActivityMainBinding binding,int page) {
        repository.getAllCharacters(adapter, binding,page);
    }

    public LiveData<PagedList<Result>> getPeoplePagedList() {
        return peoplePagedList;
    }
}
