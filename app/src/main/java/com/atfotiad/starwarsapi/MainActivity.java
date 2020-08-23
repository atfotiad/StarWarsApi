package com.atfotiad.starwarsapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.atfotiad.starwarsapi.adapters.PeopleAdapter;
import com.atfotiad.starwarsapi.databinding.ActivityMainBinding;
import com.atfotiad.starwarsapi.model.Result;
import com.atfotiad.starwarsapi.model.SwapiResponse;
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
    PagedList<Result> charactersList;

    private boolean isLoading = true;
    private int previousTotal = 0;
    private  int page = 2;
    private int viewThreshold = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        initialize();
        //peopleViewModel.getAllCharacters(peopleAdapter,mainBinding,1);
        getPeople();
    }

    protected void initialize(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        linearLayoutManager = new LinearLayoutManager(this);
        peopleViewModel = new ViewModelProvider(this).get(PeopleViewModel.class);
        //charactersList = new ArrayList<>();
        peopleAdapter = new PeopleAdapter(this,mainBinding);

        mainBinding.allCharacters.setLayoutManager(linearLayoutManager);
        mainBinding.allCharacters.setAdapter(peopleAdapter);
        mainBinding.allCharacters.setHasFixedSize(true);

        //setScrollListener();
        //page++;
    }


    private void setScrollListener(){
        mainBinding.allCharacters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount =  recyclerView.getLayoutManager().getItemCount();
                int pastVisibleItems =((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (dy >0){
                    if (isLoading){
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }

                    }
                    Log.d("VisibleItems", "onScrolled: "+visibleItemCount);
                    if (!isLoading && totalItemCount - visibleItemCount <= pastVisibleItems + viewThreshold) {
                        //perform pagination

                        page++;
                        //performPagination();
                        isLoading = true;
                    }

                }

            }
        });

    }
  /*  private void performPagination(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SwapiResponse> fetchPeople = apiInterface.getAllCharacters(page);
        fetchPeople.enqueue(new Callback<SwapiResponse>() {
            @Override
            public void onResponse(Call<SwapiResponse> call, Response<SwapiResponse> response) {
                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }
                SwapiResponse jsonResponse = response.body();
                assert jsonResponse != null;
                ArrayList<Result> allCharacters = (ArrayList<Result>) jsonResponse.getResults();
                for (int i=0 ; i<allCharacters.size();i++){
                    Log.d("Repo", "onResponse: "+ jsonResponse.getResults().get(i).getName());
                }
                peopleAdapter.addCharacters(allCharacters);
                Log.d("Item count", "onResponse: "+peopleAdapter.getItemCount());

            }

            @Override
            public void onFailure(Call<SwapiResponse> call, Throwable t) {
                Log.e("From Repo", "onFailure: " + t.getMessage());

            }
        });
*/

    private  void getPeople(){
        peopleViewModel.getPeoplePagedList().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> peopleFromLiveData) {
                charactersList = peopleFromLiveData;
                peopleAdapter.submitList(charactersList);
            }
        });

    }
}