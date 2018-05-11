package com.example.daou5____.mybakingapp.ui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daou5____.mybakingapp.R;
import com.example.daou5____.mybakingapp.data.models.Ingredients;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.RecyclerHolder> {

    Context context;

    private ArrayList<Ingredients> ingredients;


    public IngredientsAdapter(Context context , ArrayList<Ingredients> ingredients) {
        this.context = context;
        this.ingredients=ingredients;
    }

    @Override
    public IngredientsAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {

        holder.name.setText(ingredients.get(position).getIngredient());
        holder.quantity.setText(""+ingredients.get(position).getQuantity());
        holder.measure.setText(ingredients.get(position).getMeasure());

    }


    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView name,quantity,measure;

        RecyclerHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.ingredient_name);
            quantity=(TextView) itemView.findViewById(R.id.quantity);
            measure=(TextView) itemView.findViewById(R.id. measure);
        }
    }
}
