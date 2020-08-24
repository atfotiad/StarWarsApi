package com.atfotiad.starwarsapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class People {

    @SerializedName("name")
    @Expose
    private String name = null;

    @SerializedName("gender")
    @Expose
    private String gender = null;

    @SerializedName("films")
    @Expose
    private List<String> films = null;

    @SerializedName("url")
    @Expose
    private String url = null;

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getFilms() {
        return films;
    }

    public String getUrl() {
        return url;
    }


    public static final DiffUtil.ItemCallback<People> callback = new DiffUtil.ItemCallback<People>() {
        @Override
        public boolean areItemsTheSame(@NonNull People oldItem, @NonNull People newItem) {
            return oldItem.getUrl().equals(newItem.getUrl());
        }

        @Override
        public boolean areContentsTheSame(@NonNull People oldItem, @NonNull People newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };

}
