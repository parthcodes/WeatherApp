package com.example.parth.weatherapp;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parth on 10/17/14.
 */
public class WeatherModel {

       private String mDayName;
    private String mTempF;
    private String mDescription;

    public String getDayName() {
        return mDayName;
    }

    public void setDayName(String dayName) {
        mDayName = dayName;
    }

    public String getTempF() {
        return mTempF;
    }

    public void setTempF(String tempF) {
        mTempF = tempF;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public WeatherModel(String dayName, String tempF, String description){
        mDayName = dayName;
        mTempF = tempF;
        mDescription = description;
    }



    @Override
    public String toString(){
        return mDayName + " " + mTempF + " " + mDescription;
    }
}
