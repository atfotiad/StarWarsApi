package com.atfotiad.starwarsapi.viewmodel;

import android.app.Application;

import com.atfotiad.starwarsapi.model.People;
import com.atfotiad.starwarsapi.repository.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class PeopleViewModel extends AndroidViewModel {

    private Repository repository;


    public PeopleViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

    }

    public LiveData<PagedList<People>> getPeoplePagedList() {
        return repository.getPeoplePagedList();
    }
    public void refresh(){
        repository.refresh();
    }
    public LiveData<String> getNetworkState(){return repository.getNetworkState();}
}
