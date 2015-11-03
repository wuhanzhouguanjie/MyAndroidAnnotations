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

	private VelocityTracker vt = null;

	private long time1, time2 = 0, timeInterval = 0;

	// 当前最大view id
	private int currentMax = 0;

	private View currentMaxview;
	
	private int touchViewId = 0;

	private GestureDetectorCompat mDetector;

	@AfterViews
	void initView() {

		mListView.setAdapter(adapter);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenHeight = metric.heightPixels;
		measureSize(metric.heightPixels);

		// stretchanimation = new StretchAnimation(maxSize, minSize,
		// StretchAnimation.TYPE.vertical, 500);
		// // 你可以换不能给的插值器
		// stretchanimation.setInterpolator(new BounceInterpolator());
		// // 动画时间
		// stretchanimation.setDuration(800);
		// // 回调
		// stretchanimation.setOnAnimationListener(this);

		int childCount = mSliding.getChildCount();
		View child;
		LayoutParams params = null;
		int index = 0;
		int sizeValue = 0;
		currentMaxview = mSliding.getChildAt(0);
		
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
					for( int i=0;i<mSliding.getChildCount();i++ ){
						if( mSliding.getChildAt(i) == v ){
							touchViewId = i;
							break;
						}
					}
					int[] location = new int[2];
					v.getLocationOnScreen(location);
					if( event.getRawY() < (location[1] + v.getHeight()) && event.getRawY() > location[1] ){
						return mDetector.onTouchEvent(event);
					}else{
						return false;
					}
					

				}
			});
		}

		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		mDetector.onTouchEvent(event);
//		return super.onTouchEvent(event);
//	}

	

	// 1-上滑 0-下滑
	private void dealSliding(int type, View v, float distance) {
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

		clickEvent(v.getId(), distance);
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

	private void clickEvent(int viewId, float distance) {
		// distance 向上是负值，向下是正值
		View view = findViewById(viewId);
		View child;
		int childCount = mSliding.getChildCount();
		LinearLayout.LayoutParams params;
		int i = 0;
		for (; i < childCount; i++) {
			child = mSliding.getChildAt(i);
			if (view == child) {
				// params = (android.widget.LinearLayout.LayoutParams) child
				// .getLayoutParams();
				// params.weight = 1.0f;
				// params.height = maxSize;
				// child.setLayoutParams(params);
				// dealTwoViewMove(i, currentMax, distance);
				break;
			}
		}

		// params = (android.widget.LinearLayout.LayoutParams)
		// mSliding.getChildAt(currentMax)
		// .getLayoutParams();
		// params.weight = 0.0f;
		// params.height = (screenHeight - maxSize) / 3;
		// mSliding.getChildAt(currentMax).setLayoutParams(params);

		preView = view;
		currentMax = i;
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		private static final String DEBUG_TAG = "Gestures";

		@Override
		public boolean onDown(MotionEvent event) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2,
				float velocityX, float velocityY) {
			return true;
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			View view = null;
			view = mSliding.getChildAt(touchViewId);
			if( touchViewId == 0 ){
				Log.e("AAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAA");
			}else if( touchViewId == 1 ){
				Log.e("BBBBBBBBBBBBBBBBBB", "BBBBBBBBBBBBBBBBBB");
			}else if( touchViewId == 2 ){
				Log.e("CCCCCCCCCCCCCCCCCC", "CCCCCCCCCCCCCCCCCC");
			}else if( touchViewId == 3 ){
				Log.e("DDDDDDDDDDDDDDDDDD", "DDDDDDDDDDDDDDDDDD");
			}else{
				Log.e("DDDDDDDDDDDDDDDDDD", "DDDDDDDDDDDDDDDDDD " + touchViewId);
			}
			
			
			if( currentMaxview == view ){
				Log.e("return", "return");
				return false;
			}
			
			
			
//			View view1 = mSliding.getChildAt(0);
//			View view2 = mSliding.getChildAt(1);
//			View view3 = mSliding.getChildAt(2);
//			View view4 = mSliding.getChildAt(3);
			LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)view.getLayoutParams();
			LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)currentMaxview.getLayoutParams();
//			LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams)view3.getLayoutParams();
//			LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams)view4.getLayoutParams();
			int d = (int) distanceY;
			d = Math.abs(d);
			params1.height = params1.height - d;
			params2.height = params2.height + d;
			
//			if( params1.height < minSize ){
//				params1.height = minSize;
//				params2.height = maxSize;
//			}
//			
//			if( params2.height < minSize ){
//				params2.height = minSize;
//				params1.height = maxSize;
//			}
			
			int i = params1.height + params2.height;

			
			view.setLayoutParams(params2);
			currentMaxview.setLayoutParams(params1);
			

			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}

}
