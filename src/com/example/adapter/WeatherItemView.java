package com.example.adapter;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myandroidannotations.R;

@EViewGroup(R.layout.item_weather)
public class WeatherItemView extends LinearLayout{
	
	@ViewById
	TextView temperature,weather;

	public WeatherItemView(Context context) {
		super(context);
	}
	
	public void bind(WeatherInfo weatherInfo) {
		temperature.setText(weatherInfo.temperature);
		weather.setText(weatherInfo.weather);
    }

}
