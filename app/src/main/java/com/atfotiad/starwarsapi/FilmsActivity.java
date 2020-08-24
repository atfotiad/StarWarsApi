package com.atfotiad.starwarsapi;

import android.os.Bundle;

import com.atfotiad.starwarsapi.adapters.FilmsAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityFilmsBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FilmsActivity extends AppCompatActivity {
    ActivityFilmsBinding filmsBinding;
    FilmsAdapter filmsAdapter;
    LinearLayoutManager linearLayoutManager;
    List<String> films;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filmsBinding = ActivityFilmsBinding.inflate(getLayoutInflater());
        setContentView(filmsBinding.getRoot());
        initialize();

    }
    protected void initialize(){
        linearLayoutManager = new LinearLayoutManager(this);
        films = new ArrayList<>();
        films = getIntent().getStringArrayListExtra("films");
        String charName = getIntent().getStringExtra("name");
        filmsBinding.charName.setText(charName);
        FilmsViewModel filmsViewModel = new ViewModelProvider(this).get(FilmsViewModel.class);
        films= filmsViewModel.getFilms(films);

        filmsAdapter = new FilmsAdapter(films);
        filmsBinding.films.setLayoutManager(linearLayoutManager);
        filmsBinding.films.setAdapter(filmsAdapter);
        filmsBinding.films.setHasFixedSize(true);


    }


}