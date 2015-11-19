package com.example.myandroidannotations;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Util.PublicUtils;
import com.example.activity.BaiduMapActivity_;
import com.example.activity.TestActivity_;
import com.example.activity.WeatherActivity_;
import com.example.ui.ShadeTitleBar;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	// @ViewById
	// Button toWeather, toMap;
//	@ViewById
//	TextView textWeather, textMap;

	// @ViewById
	// RelativeLayout titlebar;

	@AfterViews
	void init() {
		if (android.os.Build.VERSION.SDK_INT > 18) {
			Window window = getWindow();
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		PublicUtils.init(this);
		// ButtonListener b = new ButtonListener();
		// toWeather.setOnTouchListener(b);
		// toMap.setOnTouchListener(b);

		// RelativeLayout.LayoutParams param =
		// (RelativeLayout.LayoutParams)titlebar.getLayoutParams();
		// param.setMargins(0, getStatusBarHeight(), 0, 0);
		// titlebar.setLayoutParams(param);

		final ShadeTitleBar bar = (ShadeTitleBar) findViewById(R.id.titlebar);
		bar.setTitleTextView((TextView) bar.findViewById(R.id.bar_title));
		bar.setTransparentEnabled(true, 100, 600);
		bar.setTitle("MainActivity");
		bar.setTextShadowColor(getResources().getColor(
				R.color.bar_title_text_shadow));
		bar.addViewToFadeList(findViewById(R.id.bar_left_button));
		bar.addViewToFadeList(findViewById(R.id.bar_right_button));
		bar.addViewToFadeList(findViewById(R.id.bar_title));

		final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
		scrollView.getViewTreeObserver().addOnScrollChangedListener(
				new ViewTreeObserver.OnScrollChangedListener() {

					@Override
					public void onScrollChanged() {
						bar.onScroll(scrollView.getScrollY());
					}
				});
//		scrollView.findViewById(R.id.c1).setOnClickListener(
//				new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						startActivity(new Intent(DemoActivity.this,
//								DemoActivity2.class));
//					}
//				});
//		scrollView.findViewById(R.id.c2).setOnClickListener(
//				new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						startActivity(new Intent(DemoActivity.this,
//								DemoActivity3.class));
//					}
//				});
	}

//	class ButtonListener implements OnTouchListener {
//
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//			// TODO Auto-generated method stub
//			if (v.getId() == R.id.toWeather) {
//
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					textWeather.setTextColor(getResources().getColor(
//							R.color.white));
//				}
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					textWeather.setTextColor(getResources().getColor(
//							R.color.gary));
//				}
//
//			} else if (v.getId() == R.id.toMap) {
//
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					textMap.setTextColor(getResources().getColor(R.color.white));
//				}
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					textMap.setTextColor(getResources().getColor(R.color.gary));
//				}
//
//			}
//			return false;
//		}
//
//	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

//	@Click
//	void toWeather() {
//		startActivity(new Intent(MainActivity.this, WeatherActivity_.class));
//		// startActivity(new Intent(MainActivity.this, TestActivity_.class));
//	}
//
//	@Click
//	void toMap() {
//		startActivity(new Intent(MainActivity.this, BaiduMapActivity_.class));
//	}

}
