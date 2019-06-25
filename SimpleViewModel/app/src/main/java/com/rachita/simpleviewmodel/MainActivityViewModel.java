package com.rachita.simpleviewmodel;

import android.arch.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {


    private int count;

    public int getCount() {
        return count;
    }

    public void setCount() {
       count++;
    }






}
