package com.example.daou5____.mybakingapp.ui.Activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.daou5____.mybakingapp.R;
import com.example.daou5____.mybakingapp.api.Network;
import com.example.daou5____.mybakingapp.api.OnRequestFinishedListener;
import com.example.daou5____.mybakingapp.data.models.Recipes;
import com.example.daou5____.mybakingapp.idilingResources.HomeResource;
import com.example.daou5____.mybakingapp.ui.Adapters.RecipesAdapter;
import com.example.daou5____.mybakingapp.ui.RecyclerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements OnRequestFinishedListener {

    ArrayList<Recipes> recipes;
    @BindView(R.id.recipe_rv)
    RecyclerView recycler;
    @BindView(R.id.progressBar)
    ProgressBar bar;
    @BindView(R.id.reload)
    Button reload;

    int position;
    private CountingIdlingResource mIdlingResource= new CountingIdlingResource("Loading_Data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        if(savedInstanceState == null)
        {
            mIdlingResource.increment();
            Network.getRecipes(this);
        }

        recycler.addOnItemTouchListener(new RecyclerListener(MainActivity.this, recycler, new RecyclerListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent details=new Intent(MainActivity.this,RecipeDataActivity.class);

                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("steps",
                        (ArrayList<? extends Parcelable>) recipes.get(position).getSteps());
                bundle.putParcelableArrayList("ingredients",
                        (ArrayList<? extends Parcelable>) recipes.get(position).getIngredients());
                bundle.putString("recipe_name",recipes.get(position).getName());
                details.putExtra("bundle",bundle);

                startActivity(details); }
            @Override
            public void onLongItemClick(View view, int position) { }
        }));
    }

    @Override
    public void onFailure(String message) {
        bar.setVisibility(GONE);
        reload.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this, "No internet connection !", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(Response<List<Recipes>> response) {

        recipes = (ArrayList<Recipes>) response.body();

        reload.setVisibility(GONE);
        bar.setVisibility(GONE);

        renderRecyclerView();

        mIdlingResource.decrement();
    }

    public void renderRecyclerView() {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
            recycler.setLayoutManager(linearLayoutManager);
        recycler.getLayoutManager().scrollToPosition(position);
        recycler.setAdapter(new RecipesAdapter(MainActivity.this,recipes));
    }

    public void reload(View view) {
        bar.setVisibility(View.VISIBLE);
        reload.setVisibility(GONE);
        Network.getRecipes(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("recipes",recipes);
        outState.putInt("position",((LinearLayoutManager)recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipes=savedInstanceState.getParcelableArrayList("recipes");
        position=savedInstanceState.getInt("position");
        renderRecyclerView();
        bar.setVisibility(GONE);
    }
    @VisibleForTesting
    @NonNull
    public CountingIdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
