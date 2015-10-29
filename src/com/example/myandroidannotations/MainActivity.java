package com.example.myandroidannotations;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.example.activity.BaiduMapActivity_;
import com.example.activity.WeatherActivity_;


import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewById
	Button toWeather, toMap;
	@ViewById
	TextView textWeather, textMap;
	@ViewById
	RelativeLayout titlebar;

	@AfterViews
	void init() {
		if (android.os.Build.VERSION.SDK_INT > 18) {
			Window window = getWindow();
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		ButtonListener b = new ButtonListener();
		toWeather.setOnTouchListener(b);
		toMap.setOnTouchListener(b);
		
		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)titlebar.getLayoutParams();
		param.setMargins(0, getStatusBarHeight(), 0, 0);
		titlebar.setLayoutParams(param);
	}

	class ButtonListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.toWeather) {

				if (event.getAction() == MotionEvent.ACTION_UP) {
					textWeather.setTextColor(getResources().getColor(
							R.color.white));
				}
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					textWeather.setTextColor(getResources().getColor(
							R.color.gary));
				}

			} else if (v.getId() == R.id.toMap) {

				if (event.getAction() == MotionEvent.ACTION_UP) {
					textMap.setTextColor(getResources().getColor(R.color.white));
				}
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					textMap.setTextColor(getResources().getColor(R.color.gary));
				}

			}
			return false;
		}

	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Click
	void toWeather() {
		startActivity(new Intent(MainActivity.this, WeatherActivity_.class));
	}

	@Click
	void toMap() {
		startActivity(new Intent(MainActivity.this, BaiduMapActivity_.class));
	}

}
