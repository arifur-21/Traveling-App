package com.example.tourproject11111.viewmodel;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel  extends ViewModel {

    public MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>();

    public void setNewLocation(Location location)
    {
        locationMutableLiveData.postValue(location);
    }
}
