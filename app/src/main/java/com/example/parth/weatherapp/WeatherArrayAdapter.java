package com.example.parth.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Parth on 10/17/14.
 */

public class WeatherArrayAdapter extends ArrayAdapter<WeatherModel>{
    private final List<WeatherModel> mWeatherModels;
    private final Context mContext;

    public WeatherArrayAdapter(Context context, List<WeatherModel> objects) {
        super(context, R.layout.weather_row, objects);
        mWeatherModels = objects;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.weather_row, parent, false);

        WeatherModel weatherModel = mWeatherModels.get(position);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView dayTextView = (TextView) rowView.findViewById(R.id.day);
        TextView tempTextView = (TextView) rowView.findViewById(R.id.temp);
        TextView descriptionTextView = (TextView) rowView
                .findViewById(R.id.description);

        dayTextView.setText(weatherModel.getDayName());
        tempTextView.setText(weatherModel.getTempF());
        descriptionTextView.setText(weatherModel.getDescription());

        // custom icon

        if (weatherModel.getDescription().equals("chanceflurries")) {
            imageView.setImageResource(R.drawable.chanceflurries);
        } else if (weatherModel.getDescription().equals("chancerain")) {
            imageView.setImageResource(R.drawable.chancerain);
        }
        else if (weatherModel.getDescription().equals("chancesleet")) {
            imageView.setImageResource(R.drawable.chancesleet);
        }
        else if (weatherModel.getDescription().equals("chancesnow")) {
            imageView.setImageResource(R.drawable.chancesnow);
        }
        else if (weatherModel.getDescription().equals("chancetstorms")) {
            imageView.setImageResource(R.drawable.chancetstorms);
        }
        else if (weatherModel.getDescription().equals("clear")) {
            imageView.setImageResource(R.drawable.clear);
        }
        else if (weatherModel.getDescription().equals("cloudy")) {
            imageView.setImageResource(R.drawable.cloudy);
        }
        else if (weatherModel.getDescription().equals("flurries")) {
            imageView.setImageResource(R.drawable.flurries);
        }
        else if (weatherModel.getDescription().equals("fog")) {
            imageView.setImageResource(R.drawable.fog);
        }
        else if (weatherModel.getDescription().equals("hazy")) {
            imageView.setImageResource(R.drawable.hazy);
        }
        else if (weatherModel.getDescription().equals("mostlycloudy")) {
            imageView.setImageResource(R.drawable.mostlycloudy);
        }
        else if (weatherModel.getDescription().equals("mostlysunny")) {
            imageView.setImageResource(R.drawable.mostlysunny);
        }
        else if (weatherModel.getDescription().equals("partlycloudy")) {
            imageView.setImageResource(R.drawable.partlycloudy);
        }
        else if (weatherModel.getDescription().equals("partlysunny")) {
            imageView.setImageResource(R.drawable.partlysunny);
        }
        else if (weatherModel.getDescription().equals("rain")) {
            imageView.setImageResource(R.drawable.rain);
        }
        else if (weatherModel.getDescription().equals("sleet")) {
            imageView.setImageResource(R.drawable.sleet);
        }
        else if (weatherModel.getDescription().equals("snow")) {
            imageView.setImageResource(R.drawable.snow);
        }
        else if (weatherModel.getDescription().equals("sunny")) {
            imageView.setImageResource(R.drawable.sunny);
        }
        else if (weatherModel.getDescription().equals("tstorms")) {
            imageView.setImageResource(R.drawable.tstorms);
        }

        return rowView;
    }
}

