package com.atfotiad.starwarsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.atfotiad.starwarsapi.adapters.PeopleAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityMainBinding;
import com.atfotiad.starwarsapi.model.Result;
import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    PeopleViewModel peopleViewModel;
    PeopleAdapter peopleAdapter;
    ApiInterface apiInterface;
    LinearLayoutManager linearLayoutManager;
    List<Result> charactersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        initialize();
        peopleViewModel.getAllCharacters(peopleAdapter,mainBinding);
    }

    protected void initialize(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        linearLayoutManager = new LinearLayoutManager(this);
        peopleViewModel = new ViewModelProvider(this).get(PeopleViewModel.class);
        charactersList = new ArrayList<>();
        peopleAdapter = new PeopleAdapter(charactersList,this);
        mainBinding.allCharacters.setLayoutManager(linearLayoutManager);
        mainBinding.allCharacters.setAdapter(peopleAdapter);
        mainBinding.allCharacters.setHasFixedSize(true);

    }
}