package com.example.myandroidannotations;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.example.Util.PinyingUtil;
import com.example.activity.WeatherActivity_;
import com.example.application.BaseApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewById
	EditText et;
	@ViewById
	TextView tv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Click
	void btn() {
		startActivity(new Intent(MainActivity.this, WeatherActivity_.class));
	}

}

