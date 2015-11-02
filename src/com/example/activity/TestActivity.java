package com.example.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import com.example.Util.PublicUtils;
import com.example.adapter.WeatherAdapter;
import com.example.adapter.WeatherInfo;
import com.example.myandroidannotations.R;
import com.example.ui.SlidingLayout;
import com.example.ui.StretchAnimation;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.BounceInterpolator;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@EActivity(R.layout.activity_test)
public class TestActivity extends Activity {

	@ViewById
	ListView mListView;

	@Bean
	WeatherAdapter adapter;

	@ViewById
	RelativeLayout background;

	@ViewById
	LinearLayout mSliding;

	// View可伸展最长的宽度
	private int maxSize;

	// View可伸展最小宽度
	private int minSize;

	// 当前点击的View
	private View currentView;

	// 显示最长的那个View
	private View preView;

	private int screenHeight;

	float yDown;

	float yMove;

	@AfterViews
	void initView() {

		mListView.setAdapter(adapter);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenHeight = metric.heightPixels;
		measureSize(metric.heightPixels);

		int childCount = mSliding.getChildCount();
		View child;
		LayoutParams params = null;
		int index = 0;
		int sizeValue = 0;
		for (int i = 0; i < childCount; i++) {

			child = mSliding.getChildAt(i);
			params = child.getLayoutParams();

			if (i == index) {
				preView = child;
				sizeValue = maxSize;
			} else {
				sizeValue = minSize;
			}
			params.height = sizeValue;

			child.setLayoutParams(params);

			child.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						// 手指按下时，记录按下时的纵坐标
						yDown = event.getRawY();
						break;
					case MotionEvent.ACTION_MOVE:
						// firstDraw = false;
						yMove = event.getRawY();
						if (yMove < yDown) {
							Log.e("AAAAAAAAAAAAAAAAAAAA", "上滑:" + yMove);
							dealSliding(1, v);
						} else {
							dealSliding(0, v);
							Log.e("AAAAAAAAAAAAAAAAAAAA", "下滑:" + yMove);
						}
						yDown = yMove;
						break;
					case MotionEvent.ACTION_UP:
						break;

					}
					return true;
				}
			});
		}

	}

	// 1-上滑 0-下滑
	private void dealSliding(int type, View v) {
		int id = v.getId();
		View tempView = null;

		switch (id) {

		case R.id.lin_1:
			tempView = mSliding.getChildAt(0);
			break;
		case R.id.lin_2:
			tempView = mSliding.getChildAt(1);
			break;
		case R.id.lin_3:
			tempView = mSliding.getChildAt(2);
			break;
		case R.id.lin_4:
			tempView = mSliding.getChildAt(3);
			break;
		}

		if (tempView == preView) {
			return;
		} else {
			currentView = tempView;
		}

		clickEvent(v.getId());
	}

	/**
	 * 测量View 的 max min 长度 这里你可以根据你的要求设置max
	 * 
	 * @param screenSize
	 * @param index
	 *            从零开始
	 */
	private void measureSize(int layoutSize) {
		int halfWidth = layoutSize / 2;
		maxSize = halfWidth;
		minSize = (layoutSize - maxSize) / (mSliding.getChildCount() - 1);

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

	@ItemClick
	void WeatherItemClick(WeatherInfo info) {
		Toast.makeText(this, info.temperature, Toast.LENGTH_SHORT).show();
	}

	private void clickEvent(int viewId) {
		View view = findViewById(viewId);
		View child;
		int childCount = mSliding.getChildCount();
		LinearLayout.LayoutParams params;
		for (int i = 0; i < childCount; i++) {
			child = mSliding.getChildAt(i);
			if (view == child) {
				params = (android.widget.LinearLayout.LayoutParams) child
						.getLayoutParams();
				params.weight = 1.0f;
				params.height = maxSize;
				child.setLayoutParams(params);

			} else {
				params = (android.widget.LinearLayout.LayoutParams) child
						.getLayoutParams();
				params.weight = 0.0f;
				params.height = (screenHeight - maxSize) / 3;
				child.setLayoutParams(params);
			}
		}

		preView = view;
	}

}
