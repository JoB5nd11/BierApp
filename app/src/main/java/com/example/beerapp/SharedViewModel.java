package com.example.beerapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {
    private MutableLiveData <ArrayList <Beer>> beerL = new MutableLiveData<>();
    private MutableLiveData <ArrayList <Beer>> favoritesL = new MutableLiveData<>();

    public void setFavoritesL(ArrayList <Beer> fL) {
        favoritesL.setValue(fL);
    }

    public LiveData <ArrayList <Beer>> getFavoritesL() {
        return favoritesL;
    }

    public void setBeerL(ArrayList <Beer> bL) {
        beerL.setValue(bL);
    }

    public LiveData <ArrayList <Beer>> getBeerL() {
        return beerL;
    }

}
