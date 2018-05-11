package com.example.daou5____.mybakingapp.ui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.daou5____.mybakingapp.R;
import com.example.daou5____.mybakingapp.data.models.Recipes;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecyclerHolder> {

    Context context;
    private ArrayList<Recipes> recipes;


    public RecipesAdapter(Context context, ArrayList<Recipes> recipes) {
        this.context = context;
        this.recipes = recipes;
    }


    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {

        holder.recipeName.setText(recipes.get(position).getName());

        switch (position) {
            case 0:
                holder.recipeImage.setImageResource(R.drawable.nutella_pie);
                break;
            case 1:
                holder.recipeImage.setImageResource(R.drawable.brownies);
                break;
            case 2:
                holder.recipeImage.setImageResource(R.drawable.yellow_cake);
                break;
            case 3:
                holder.recipeImage.setImageResource(R.drawable.cheesecake);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        ImageView recipeImage;

        RecyclerHolder(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            recipeImage = (ImageView) itemView.findViewById(R.id.image);

        }
    }


}

