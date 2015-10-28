package com.example.myandroidannotations;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import com.example.activity.BaiduMapActivity_;
import com.example.activity.WeatherActivity_;


import android.app.Activity;
import android.content.Intent;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {


	@Click
	void toWeather() {
		startActivity(new Intent(MainActivity.this, WeatherActivity_.class));
	}
	
	@Click
	void toMap(){
		startActivity(new Intent(MainActivity.this, BaiduMapActivity_.class));
	}

}

