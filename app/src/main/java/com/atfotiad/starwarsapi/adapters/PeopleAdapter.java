package com.atfotiad.starwarsapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.atfotiad.starwarsapi.databinding.CharacterRowBinding;
import com.atfotiad.starwarsapi.model.Result;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleHolder> {
    private List<Result> charactersList;
    private Context context;

    public PeopleAdapter(List<Result> charactersList, Context context) {
        this.charactersList = charactersList;
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
        Result currentCharacter = charactersList.get(position);
        holder.rowBinding.charName.setText(currentCharacter.getName());
        holder.rowBinding.gender.setText(currentCharacter.getGender());


    }

    @Override
    public int getItemCount() {
        if(charactersList != null){
            return charactersList.size();
        }
        else return 0;
    }
    public void addCharacters(List<Result> characters){
        charactersList.addAll(characters);
        notifyDataSetChanged();
    }

    public static class PeopleHolder extends RecyclerView.ViewHolder{
        private CharacterRowBinding rowBinding;
        public PeopleHolder(@NonNull CharacterRowBinding binding) {
            super(binding.getRoot());
            rowBinding = binding;
        }
    }
}
