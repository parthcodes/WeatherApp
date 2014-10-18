package com.example.parth.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class SettingsActivity extends Activity {


    Spinner spinnerTemp;
    Spinner spinnerLang;
    Spinner spinnerDay;
    Button mButton;

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        mButton = (Button)findViewById(R.id.button_submit);
        spinnerTemp =(Spinner)findViewById(R.id.spinner_temprature);
        spinnerDay =(Spinner)findViewById(R.id.spinner_days);

        spinnerLang =(Spinner)findViewById(R.id.spinner_language);
        sharedpreferences = getSharedPreferences(WeatherActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        spinnerDay.setSelection(Integer.valueOf(sharedpreferences.getString("days",""))-1);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick();
            }
        });

    }

    void handleClick(){
        String temp = spinnerTemp.getSelectedItem().toString();
        String lang = spinnerLang.getSelectedItem().toString();
        String days = spinnerDay.getSelectedItem().toString();
        sharedpreferences = getSharedPreferences(WeatherActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("temperature",temp);
        editor.putString("days",days);
        editor.putString("language",lang);

        editor.commit();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
