package com.example.daou5____.mybakingapp.ui.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.daou5____.mybakingapp.R;
import com.example.daou5____.mybakingapp.ui.Fragment.StepsDataFragment;

public class StepsDataActivity extends AppCompatActivity{

    StepsDataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_data_activity);

        if(savedInstanceState == null)
        {
            Bundle bundle = getIntent().getExtras();

            if(bundle.containsKey("name"))
            {
                getSupportActionBar().setTitle(bundle.getString("name")+" Steps");
            }
            bundle.putBoolean("tablet",false);

            dataFragment = new StepsDataFragment();
            dataFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .add(R.id.data_fragment, dataFragment)
                    .commit();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState,"fragment", dataFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataFragment = (StepsDataFragment) getFragmentManager().getFragment(savedInstanceState,"fragment");
        if(dataFragment.isAdded())
        {
            return;
        }
        getFragmentManager().beginTransaction()
                .add(R.id.data_fragment, dataFragment)
                .commit();
    }
}
