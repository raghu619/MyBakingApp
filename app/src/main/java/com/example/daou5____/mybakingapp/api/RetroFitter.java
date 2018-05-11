package com.example.daou5____.mybakingapp.api;

import com.example.daou5____.mybakingapp.data.models.Recipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetroFitter {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipes>> getRecipes();
}
