package com.atfotiad.starwarsapi;

import android.app.Application;

import com.atfotiad.starwarsapi.adapters.PeopleAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityMainBinding;
import com.atfotiad.starwarsapi.repository.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PeopleViewModel extends AndroidViewModel {
    private Repository repository;
    public PeopleViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }
    public void getAllCharacters(PeopleAdapter adapter, ActivityMainBinding binding) {
        repository.getAllCharacters(adapter, binding);
    }
}
