package com.example.parth.weatherapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.*;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;
import java.util.ArrayList;

import java.util.List;


public class WeatherActivity extends ListActivity implements LocationListener{

    private String latitude;
    private String longitude;
    public static final String currentTempretureUrl = "http://api.wunderground.com/api/c7001172a942fa71/forecast10day/q/";
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    TextView t;
    public static final String MyPREFERENCES = "myPreferences";
    Button mButton;
    private String provider;
    protected boolean gps_enabled,network_enabled;
    private ArrayList<String> list = new ArrayList<String>();
    Integer[] imageID;
    String[] conditions;
    String[] temperature;
    List<WeatherModel> mModels = new ArrayList<WeatherModel>();
    Button zipCodeButton;
    EditText zipCodeText;

    //Initialize Sharedpreference

    private SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        t = (TextView)findViewById(R.id.weather_box);

        mButton = (Button)findViewById(R.id.load_button);
        zipCodeButton = (Button)findViewById(R.id.search_zip);
        zipCodeText = (EditText)findViewById(R.id.edittext_zipcode);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);

        sharedpreferences = getSharedPreferences(WeatherActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        zipCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFromZipcode();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    loadFromApi();
            }
        });


       //customAdapter experiment




       /* CustomList adapter = new
                CustomList(this, list, imageID);
       mListView.setAdapter(adapter);*/


        //second array adapter experiment

    }


    private void loadFromZipcode(){

        if(sharedpreferences.contains("language")==false){
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("temperature", "fahrenheit");
            editor.putString("days","3");
            editor.putString("language","English");
        }

        if(zipCodeText.getText().toString().matches("")){
            Toast.makeText(WeatherActivity.this,"Empty Zipcode!",Toast.LENGTH_SHORT).show();
        }
        else{
            String zipCodeadd = zipCodeText.getText().toString()+".json";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(currentTempretureUrl+zipCodeadd, new TextHttpResponseHandler() {

                @Override
                public void onStart(){
                }

                @Override
                public void onFinish(){

                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {

                    JSONObject json;
                    try {
                        json =new JSONObject(s);
                        //Toast.makeText(WeatherActivity.this,"getting!",Toast.LENGTH_SHORT).show();
                        parseAndDisplayJson(json);
                    }
                    catch(Exception e){
                        Toast.makeText(WeatherActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }
    private void loadFromApi(){
        //3rd party library example
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
        if(sharedpreferences.contains("language")==false){
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("temperature", "fahrenheit");
            editor.putString("days","3");
            editor.putString("language","English");
        }

        AsyncHttpClient client = new AsyncHttpClient();
        String addInfo = latitude+","+longitude+".json";
        client.get(currentTempretureUrl+addInfo, new TextHttpResponseHandler() {

            @Override
            public void onStart(){
            }

            @Override
            public void onFinish(){

            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                JSONObject json;
                try {
                    json =new JSONObject(s);
                    Toast.makeText(WeatherActivity.this,"getting!",Toast.LENGTH_SHORT).show();
                    parseAndDisplayJson(json);
                }
                catch(Exception e){
                    Toast.makeText(WeatherActivity.this,"Error in Parsing!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void parseAndDisplayJson(JSONObject json) {


        double conversionHandler = 0;
        int x=0;


        //make list null.
       mModels.clear();

        String foreCastDisplayHigh = null, foreCastDisplayLow = null, discriptionMessage, weekDay;

        try {
            JSONObject parseObject = json.getJSONObject("forecast").getJSONObject("simpleforecast"); // getting current temprature:

            JSONArray forecastArray = parseObject.getJSONArray("forecastday");

            //Defult is 3 day forecast.

            while (x <= Integer.valueOf(sharedpreferences.getString("days", ""))){
                foreCastDisplayHigh = forecastArray.getJSONObject(x).getJSONObject("high").getString(sharedpreferences.getString("temperature","").toLowerCase());
                foreCastDisplayLow = forecastArray.getJSONObject(x).getJSONObject("low").getString(sharedpreferences.getString("temperature","").toLowerCase());
                discriptionMessage = forecastArray.getJSONObject(x).getString("icon");
                weekDay = forecastArray.getJSONObject(x).getJSONObject("date").getString("weekday");

                //Toast.makeText(WeatherActivity.this,"jason parsed!",Toast.LENGTH_SHORT).show();

                mModels.add(new WeatherModel(weekDay,foreCastDisplayLow+" / "+foreCastDisplayHigh,discriptionMessage));

                x++;
        }

        }
        catch(Exception e){
            Toast.makeText(WeatherActivity.this,"Error in data.",Toast.LENGTH_SHORT).show();
        }

        String fOrC = sharedpreferences.getString("temperature","");
        t.setText("Temperatures are in "+ fOrC+", and Low / High for the day.");
        setupList();

    }

    public void setupList(){


        final WeatherArrayAdapter adapter =
                new WeatherArrayAdapter(this,mModels);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(settingsIntent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        loadFromApi();
    }



    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        latitude = String.valueOf(lat);
        longitude = String.valueOf(lng);

        //t.setText("latitude :" + lat +"long: "+ lng);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude", "enabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude", "disable");
    }
}
