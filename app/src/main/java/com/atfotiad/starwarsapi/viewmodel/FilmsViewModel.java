package com.atfotiad.starwarsapi.viewmodel;

import android.app.Application;

import com.atfotiad.starwarsapi.repository.Repository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class FilmsViewModel extends AndroidViewModel {
    private Repository repository;

    public FilmsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

    }
    public List<String> getFilms(List<String> films) {return repository.getFilmNames(films);}
}
