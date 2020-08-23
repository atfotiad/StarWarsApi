package com.atfotiad.starwarsapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atfotiad.starwarsapi.FilmsActivity;
import com.atfotiad.starwarsapi.databinding.ActivityMainBinding;
import com.atfotiad.starwarsapi.databinding.CharacterRowBinding;
import com.atfotiad.starwarsapi.model.Result;
import com.atfotiad.starwarsapi.model.SwapiResponse;
import com.atfotiad.starwarsapi.retrofit.ApiClient;
import com.atfotiad.starwarsapi.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleAdapter extends PagedListAdapter<Result,PeopleAdapter.PeopleHolder> {
    private Context context;
    private ActivityMainBinding mainBinding;


    public PeopleAdapter( Context context, ActivityMainBinding binding) {
        super(Result.callback);
        this.context = context;
        this.mainBinding = binding;
    }

    @NonNull
    @Override
    public PeopleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PeopleHolder(CharacterRowBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleHolder holder, int position) {
        Result currentCharacter = getItem(position);

        holder.rowBinding.charName.setText(currentCharacter.getName());
        holder.rowBinding.gender.setText(currentCharacter.getGender());

        holder.rowBinding.charCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, FilmsActivity.class);
            intent.putExtra("name",currentCharacter.getName());
            intent.putStringArrayListExtra("films",(ArrayList<String>) currentCharacter.getFilms());

            context.startActivity(intent);
        });




    }



    /*@Override
    public int getItemCount() {
        if(charactersList != null){
            return charactersList.size();
        }
        else return 0;
    }


    public void addCharacters(List<Result> characters){
        charactersList.addAll(characters);
        notifyDataSetChanged();
    }*/


    public static class PeopleHolder extends RecyclerView.ViewHolder{
        private CharacterRowBinding rowBinding;
        public PeopleHolder(@NonNull CharacterRowBinding binding) {
            super(binding.getRoot());
            rowBinding = binding;
        }
    }
}
