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
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@EActivity(R.layout.activity_test)
public class TestActivity extends Activity implements View.OnClickListener,
		StretchAnimation.AnimationListener {

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


	float yDown;

	float yMove;


	private StretchAnimation stretchanimation;

	@AfterViews
	void initView() {

		mListView.setAdapter(adapter);

//		DisplayMetrics metric = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metric);
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
		Log.e("BBBBBBBBBBBBBBBBBBBB", dm.heightPixels+" " + dm.widthPixels+ " ");
		measureSize(dm.heightPixels);

		stretchanimation = new StretchAnimation(maxSize, minSize,
				StretchAnimation.TYPE.vertical, 500);
		// 你可以换不能给的插值器
		stretchanimation.setInterpolator(new DecelerateInterpolator ());
		// 动画时间
		stretchanimation.setDuration(800);
		// 回调
		stretchanimation.setOnAnimationListener(this);

		int childCount = mSliding.getChildCount();
		View child;
		LayoutParams params = null;
		int index = 0;
		int sizeValue = 0;

		for (int i = 0; i < childCount; i++) {

			child = mSliding.getChildAt(i);
			child.setOnClickListener(this);
			params = child.getLayoutParams();

			if (i == index) {
				preView = child;
				sizeValue = maxSize;
			} else {
				sizeValue = minSize;
			}
			params.height = sizeValue;
			child.setLayoutParams(params);
		}

	}

	@Override
	public void onClick(View v) {
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
		stretchanimation.startAnimation(currentView);
//		clickEvent(currentView);
		onOffClickable(false);
		
	}

	private void clickEvent(View view) {
		View child;
		int childCount = mSliding.getChildCount();
		LinearLayout.LayoutParams params;
		for (int i = 0; i < childCount; i++) {
			child = mSliding.getChildAt(i);
			if (preView == child) {
				params = (android.widget.LinearLayout.LayoutParams) child
						.getLayoutParams();

				if (preView != view) {
					params.weight = 1.0f;
					params.height = maxSize;
				}
				child.setLayoutParams(params);

			} else {
				params = (android.widget.LinearLayout.LayoutParams) child
						.getLayoutParams();
				params.weight = 0.0f;
				params.height = minSize;
				child.setLayoutParams(params);
			}
		}
		preView = view;

	}

	// LinearLayout下所有childView 可点击开关
	// 当动画在播放时应该设置为不可点击，结束时设置为可点击
	private void onOffClickable(boolean isClickable) {
		View child;
		int childCount = mSliding.getChildCount();
		for (int i = 0; i < childCount; i++) {
			child = mSliding.getChildAt(i);
			child.setClickable(isClickable);
		}
	}

	/**
	 * 测量View 的 max min 长度 这里你可以根据你的要求设置max
	 * 
	 * @param screenSize
	 * @param index
	 *            从零开始
	 */
	private void measureSize(int layoutSize) {
		
		minSize = (layoutSize/2) / (mSliding.getChildCount() - 1);
		maxSize = layoutSize - 3*minSize;
		Log.e("AAAAAAAAAAAAAAAAAA", layoutSize + " " + maxSize + " " + minSize);
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



	@Override
	public void animationEnd(View v) {
		// TODO Auto-generated method stub
		onOffClickable(true);
		
		for( int i=0;i<mSliding.getChildCount();i++ ){
			Log.e("end", " " + mSliding.getChildAt(i).getHeight());
		}
	}

}
