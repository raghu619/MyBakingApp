package com.example.daou5____.mybakingapp.api;

import com.example.daou5____.mybakingapp.data.models.Recipes;

import java.util.List;

import retrofit2.Response;

public interface OnRequestFinishedListener {
    void onFailure(String message);

    void onResponse(Response<List<Recipes>> response);
}
