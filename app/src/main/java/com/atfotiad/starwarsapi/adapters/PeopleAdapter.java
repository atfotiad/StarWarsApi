package com.atfotiad.starwarsapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.atfotiad.starwarsapi.view.FilmsActivity;
import com.atfotiad.starwarsapi.databinding.CharacterRowBinding;
import com.atfotiad.starwarsapi.model.People;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PeopleAdapter extends PagedListAdapter<People,PeopleAdapter.PeopleHolder> {
    private Context context;

    public PeopleAdapter( Context context) {
        super(People.callback);
        this.context = context;

    }

    @NonNull
    @Override
    public PeopleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PeopleHolder(CharacterRowBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleHolder holder, int position) {
        People currentCharacter = getItem(position);

        holder.rowBinding.charName.setText(Objects.requireNonNull(currentCharacter).getName());
        holder.rowBinding.gender.setText(currentCharacter.getGender());

        holder.rowBinding.charCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, FilmsActivity.class);
            intent.putExtra("name",currentCharacter.getName());
            intent.putStringArrayListExtra("films",(ArrayList<String>) currentCharacter.getFilms());

            context.startActivity(intent);
        });


    }


    public static class PeopleHolder extends RecyclerView.ViewHolder{
        private CharacterRowBinding rowBinding;
        public PeopleHolder(@NonNull CharacterRowBinding binding) {
            super(binding.getRoot());
            rowBinding = binding;
        }
    }
}
