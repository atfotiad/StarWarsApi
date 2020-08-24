package com.atfotiad.starwarsapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SwapiResponse {

    @SerializedName("results")
    @Expose
    private List<People> people = null;

    public List<People> getPeople() {
        return people;
    }


}
