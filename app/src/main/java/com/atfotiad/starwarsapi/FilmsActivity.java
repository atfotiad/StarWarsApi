package com.atfotiad.starwarsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.atfotiad.starwarsapi.adapters.FilmsAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityFilmsBinding;

import java.util.ArrayList;
import java.util.List;

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
        films= getFilmNames(films);

        filmsAdapter = new FilmsAdapter(films);
        filmsBinding.films.setLayoutManager(linearLayoutManager);
        filmsBinding.films.setAdapter(filmsAdapter);
        filmsBinding.films.setHasFixedSize(true);




    }
    protected List<String>  getFilmNames(List<String> films){
        ArrayList<String> newFilms = new ArrayList<>();
        for (String film :films ){
            String name = getNameFromUrl(film);
            Log.d("from names", "getFilmNameFromUrl: "+ name);
            newFilms.add(name);

        }
        return newFilms;
    }

    protected String getNameFromUrl(String film){
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