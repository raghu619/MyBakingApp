package com.example.daou5____.mybakingapp.ui;

import com.example.daou5____.mybakingapp.data.models.Steps;

import java.util.ArrayList;

public interface DataFragmentListener {

    void setStep(int index , ArrayList<Steps> steps);


    void setCurrent(int index);
}