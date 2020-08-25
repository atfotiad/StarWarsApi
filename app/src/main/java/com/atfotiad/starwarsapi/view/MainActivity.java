package com.atfotiad.starwarsapi.view;

import android.os.Bundle;
import android.view.View;

import com.atfotiad.starwarsapi.adapters.PeopleAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityMainBinding;
import com.atfotiad.starwarsapi.model.People;
import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;
import com.atfotiad.starwarsapi.viewmodel.PeopleViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    PeopleViewModel peopleViewModel;
    PeopleAdapter peopleAdapter;
    ApiInterface apiInterface;
    LinearLayoutManager linearLayoutManager;
    PagedList<People> charactersList;
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        initialize();
        getPeople();
        getNetwork();
    }

    protected void initialize(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        linearLayoutManager = new LinearLayoutManager(this);
        peopleViewModel = new ViewModelProvider(this).get(PeopleViewModel.class);
        peopleAdapter = new PeopleAdapter(this);

        mainBinding.allCharacters.setLayoutManager(linearLayoutManager);
        mainBinding.allCharacters.setAdapter(peopleAdapter);
        mainBinding.allCharacters.setHasFixedSize(true);

        refreshLayout = mainBinding.swipeRefresh;
        refreshLayout.setOnRefreshListener(() -> {
            peopleViewModel.refresh();
            getPeople();
            refreshLayout.setRefreshing(false);
            mainBinding.allCharacters.setAdapter(peopleAdapter);
            mainBinding.networkState.setVisibility(View.GONE);


        });

    }

    protected  void getPeople(){
        peopleViewModel.getPeoplePagedList().observe(this, peopleFromLiveData -> {
            charactersList = peopleFromLiveData;
            peopleAdapter.submitList(charactersList);
        });

    }

    protected void getNetwork(){
        peopleViewModel.getNetworkState().observe(this, s -> {
            mainBinding.networkState.setText(s);
            mainBinding.networkState.setVisibility(View.VISIBLE);
        });
    }
}