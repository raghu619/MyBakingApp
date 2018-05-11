package com.example.daou5____.mybakingapp.api;

import com.example.daou5____.mybakingapp.data.models.Recipes;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static List<Recipes> recipes;
    final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public static void getRecipes(final OnRequestFinishedListener listener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroFitter retrofitClass = retrofit.create(RetroFitter.class);

        Call<List<Recipes>> call = retrofitClass.getRecipes();
        call.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                listener.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}