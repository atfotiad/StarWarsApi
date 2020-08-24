package com.atfotiad.starwarsapi;

import android.os.Bundle;

import com.atfotiad.starwarsapi.adapters.PeopleAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityMainBinding;
import com.atfotiad.starwarsapi.model.People;
import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    PeopleViewModel peopleViewModel;
    PeopleAdapter peopleAdapter;
    ApiInterface apiInterface;
    LinearLayoutManager linearLayoutManager;
    PagedList<People> charactersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        initialize();
        getPeople();
    }

    protected void initialize(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        linearLayoutManager = new LinearLayoutManager(this);
        peopleViewModel = new ViewModelProvider(this).get(PeopleViewModel.class);
        peopleAdapter = new PeopleAdapter(this);

        mainBinding.allCharacters.setLayoutManager(linearLayoutManager);
        mainBinding.allCharacters.setAdapter(peopleAdapter);
        mainBinding.allCharacters.setHasFixedSize(true);

    }

    protected  void getPeople(){
        peopleViewModel.getPeoplePagedList().observe(this, peopleFromLiveData -> {
            charactersList = peopleFromLiveData;
            peopleAdapter.submitList(charactersList);
        });

    }
}