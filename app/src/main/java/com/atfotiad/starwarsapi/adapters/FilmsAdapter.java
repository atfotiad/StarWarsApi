package com.atfotiad.starwarsapi.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.atfotiad.starwarsapi.databinding.FilmRowBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.FilmsHolder> {
    private List<String> films;

    public FilmsAdapter(List<String> films) {
        this.films = films;
    }

    @NonNull
    @Override
    public FilmsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilmsHolder(FilmRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsHolder holder, int position) {
        String currentString = films.get(position);
        holder.rowBinding.filmName.setText(currentString);

    }

    @Override
    public int getItemCount() {
        if(films != null){
            return films.size();
        }
        else return 0;
    }


    public static class FilmsHolder extends RecyclerView.ViewHolder {
        private FilmRowBinding rowBinding;

        public FilmsHolder(@NonNull FilmRowBinding binding) {
            super(binding.getRoot());
            rowBinding = binding;
        }
    }
}
