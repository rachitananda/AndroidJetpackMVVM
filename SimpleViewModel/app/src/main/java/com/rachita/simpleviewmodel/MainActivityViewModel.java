package com.rachita.simpleviewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {


    private int count;
    private MutableLiveData<Integer> liveCount = new MutableLiveData<>();

    public int getCount() {
        return count;
    }

    public MutableLiveData<Integer> getLiveCount(){
        liveCount.setValue(count);
        return liveCount;
    }

    public void setCount() {
       count++;
        liveCount.setValue(count);
    }






}
