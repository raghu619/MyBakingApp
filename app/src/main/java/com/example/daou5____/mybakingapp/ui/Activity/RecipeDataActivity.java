package com.example.daou5____.mybakingapp.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.daou5____.mybakingapp.R;
import com.example.daou5____.mybakingapp.data.models.Steps;
import com.example.daou5____.mybakingapp.ui.DataFragmentListener;
import com.example.daou5____.mybakingapp.ui.Fragment.StepFragment;
import com.example.daou5____.mybakingapp.ui.Fragment.StepsDataFragment;

import java.util.ArrayList;

public class RecipeDataActivity extends AppCompatActivity implements DataFragmentListener {

    FrameLayout dataFragment;
    boolean Tablet;
    private ArrayList<Steps> steps;
    String name;
    StepsDataFragment detailsFragment;
    StepFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_data_activity);

        dataFragment = (FrameLayout) findViewById(R.id.second_fragment);
        Tablet = true;
        Bundle extras = getIntent().getBundleExtra("bundle");
        name = extras.getString("recipe_name");
        getSupportActionBar().setTitle(name);
        steps = extras.getParcelableArrayList("steps");
        extras.putBoolean("tablet", (dataFragment != null));

        if (savedInstanceState == null) {
            fragment = new StepFragment();
            fragment.setFragmentListener(this);
            fragment.setArguments(extras);
            getFragmentManager().beginTransaction().add(R.id.first_fragment, fragment).commit();
            //checking if screen size greater than 600dp
            if (dataFragment == null) {
                Tablet = false;
            } else {
                this.setStep(0, steps);
            }
        } else {
            fragment= (StepFragment) getFragmentManager().getFragment(savedInstanceState,"main");
            fragment.setFragmentListener(this);


            if (!fragment.isAdded())
                getFragmentManager().beginTransaction().add(R.id.first_fragment, fragment).commit();

            if(detailsFragment !=null)
            {
                detailsFragment= (StepsDataFragment) getFragmentManager().getFragment(savedInstanceState,"detail");
                getFragmentManager().beginTransaction().replace(R.id.second_fragment, detailsFragment).commit();
            }
        }
    }

    @Override
    public void setStep(int index, ArrayList<Steps> steps) {
        if (!Tablet) {
            Intent intent = new Intent(this, StepsDataActivity.class);
            intent.putExtra("steps", steps);
            intent.putExtra("current", index);
            intent.putExtra("name", name);
            startActivity(intent);
        } else {
            detailsFragment = new StepsDataFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steps", steps);
            detailsFragment.setFragmentListener(this);
            bundle.putInt("current", index);
            bundle.putBoolean("tablet", true);
            detailsFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.second_fragment, detailsFragment).commit();
        }
    }

    @Override
    public void setCurrent(int index) {
        if (Tablet) {
            fragment.updateView(index);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "main", fragment);

        if (Tablet && dataFragment !=null) {
            try{
                getFragmentManager().putFragment(outState, "detail", detailsFragment);
            }catch (NullPointerException e) {}

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (dataFragment == null) {
            Tablet = false;
        }
    }
}