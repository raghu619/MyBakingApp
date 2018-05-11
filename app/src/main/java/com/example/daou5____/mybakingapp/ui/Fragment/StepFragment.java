package com.example.daou5____.mybakingapp.ui.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daou5____.mybakingapp.R;
import com.example.daou5____.mybakingapp.data.models.Ingredients;
import com.example.daou5____.mybakingapp.data.models.Steps;
import com.example.daou5____.mybakingapp.ui.Adapters.IngredientsAdapter;
import com.example.daou5____.mybakingapp.ui.Adapters.StepsAdapter;
import com.example.daou5____.mybakingapp.ui.DataFragmentListener;
import com.example.daou5____.mybakingapp.ui.RecyclerListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {
    DataFragmentListener listener;

    @BindView(R.id.steps_rv)
    RecyclerView recycler;
    @BindView(R.id.ingredient_rv)
    RecyclerView ingredientList;


    ArrayList<Steps> steps;
    ArrayList<Ingredients> ingredients;

    int[] trackers;
    int index;

    boolean tablet;
    LinearLayoutManager ingredientsManager, stepsManager;

    int x1, x2;

    public void setFragmentListener(DataFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steps_fragment, container, false);
        ButterKnife.bind(this, rootView);

        stepsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ingredientsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (savedInstanceState == null) {
            Bundle extra = getArguments();
            tablet = extra.getBoolean("tablet", false);
            steps = extra.getParcelableArrayList("steps");
            ingredients = extra.getParcelableArrayList("ingredients");

            index = 0;
        } else {
            tablet = savedInstanceState.getBoolean("tablet", false);
            steps = savedInstanceState.getParcelableArrayList("steps");
            ingredients = savedInstanceState.getParcelableArrayList("ingredients");
            index = savedInstanceState.getInt("position");

            x1 = savedInstanceState.getInt("x1");
            x2 = savedInstanceState.getInt("x2");
        }
        trackers = new int[steps.size()];
        if (tablet) {
            trackers[index] = 1;
        }

        ingredientList.setLayoutManager(ingredientsManager);
        ingredientList.setAdapter(new IngredientsAdapter(getActivity(), ingredients));
        ingredientList.addItemDecoration(new DividerItemDecoration(ingredientList.getContext(), DividerItemDecoration.VERTICAL));
        if (x1 != 0) {
            ingredientList.getLayoutManager().scrollToPosition(x1);
        }

        // Create a list of words
        recycler.setLayoutManager(stepsManager);
        recycler.setAdapter(new StepsAdapter(getActivity(), steps, trackers));
        recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL));
        if (x2 != 0) {
            recycler.getLayoutManager().scrollToPosition(x2);
        }

        recycler.addOnItemTouchListener(new RecyclerListener(getActivity(), recycler, new RecyclerListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        listener.setStep(position, steps);
                        updateView(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
        if (tablet) {
            updateView(index);
            listener.setStep(index, steps);
        }
        return rootView;
    }

    public void updateView(int index) {
        this.index = index;
        if (!tablet) {
            return;
        }
        trackers = new int[steps.size()];
        try {
            trackers[index] = 1;
            ((StepsAdapter) recycler.getAdapter()).trackers = trackers;
            recycler.getAdapter().notifyDataSetChanged();
            recycler.scrollToPosition(index);
        } catch (ArrayIndexOutOfBoundsException E) {
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("steps", steps);
        outState.putParcelableArrayList("ingredients", ingredients);
        outState.putBoolean("tablet", tablet);
        outState.putInt("position", index);
        outState.putInt("x2", ((LinearLayoutManager) recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
        outState.putInt("x1", ((LinearLayoutManager) ingredientList.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
    }
}
