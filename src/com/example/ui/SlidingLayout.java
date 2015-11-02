package com.example.ui;

import com.example.Util.PublicUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;

public class SlidingLayout extends LinearLayout {

	private int[] mLocation = new int[2];

//	private float bottomMargin;

	private int INVALIDATE_DURATION = 40;

	/**
	 * 上侧布局的参数，通过此参数来重新确定上侧布局的宽度，以及更改topMargin的值。
	 */
//	private MarginLayoutParams bottomLayoutParams;

	/**
	 * 上侧布局对象。
	 */
//	private View bottomLayout;

	/**
	 * 记录手指按下时的纵坐标。
	 */
	private float yDown;

	/**
	 * 记录手指移动时的纵坐标。
	 */
	private float yMove;

	/**
	 * 记录手机抬起时的纵坐标。
	 */
	private float yUp;

	/**
	 * 状态栏高度
	 */
	private int statusHeight;

	private int MAX_HEIGHT, MIN_HEIGHT;

	private boolean firstDraw = true;

	private int SCREEN_HEIGHT;

	public SlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		statusHeight = PublicUtils.getStatusBarHeight(context);
//		bottomMargin = PublicUtils.dip2px(context, 50);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		MAX_HEIGHT = wm.getDefaultDisplay().getWidth() / 2;
		MIN_HEIGHT = wm.getDefaultDisplay().getWidth() / 4;
		SCREEN_HEIGHT = wm.getDefaultDisplay().getWidth();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
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
				dealScroll(yMove, 0);

			} else {
				Log.e("AAAAAAAAAAAAAAAAAAAA", "下滑:" + yMove);
				dealScroll(yMove, 1);
			}
			yDown = yMove;
			break;
		case MotionEvent.ACTION_UP:
			break;

		}
		return true;
	};

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// if( firstDraw ){
		// setMeasuredDimension(widthMeasureSpec,MIN_HEIGHT);
		// }else{
		// if( this.getHeight() > MAX_HEIGHT ){
		// setMeasuredDimension(widthMeasureSpec,MAX_HEIGHT);
		// }else if( this.getHeight() < MIN_HEIGHT ){
		// setMeasuredDimension(widthMeasureSpec,MIN_HEIGHT);
		// }
		// }
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// if (this.getHeight() > MAX_HEIGHT || this.getHeight() < MIN_HEIGHT) {
		// return;
		// }
//		View view1 = getChildAt(0);
//		MarginLayoutParams param1 = (MarginLayoutParams) view1
//				.getLayoutParams();
//		param1.bottomMargin = (int) view1Bottom;
//		view1.setLayoutParams(param1);
//		
//		View view2 = getChildAt(1);
//		MarginLayoutParams param2 = (MarginLayoutParams) view2
//				.getLayoutParams();
//		param2.topMargin = (int) (SCREEN_HEIGHT - view1Bottom);
//		view2.setLayoutParams(param2);
//		postInvalidateDelayed(INVALIDATE_DURATION);

	}
	
	private int scrollSpeed = 10;
	
	private int view1Bottom = 0;

	// 0 up ; 1 down
	private void dealScroll(float ymove, int upOrDown) {
		// ymove 包含了状态栏的高度75
		// 计算child view高度
		int childHeight[] = new int[4];

		for (int loop = 0; loop < 4; loop++) {
			childHeight[loop] = this.getChildAt(loop).getHeight();
		}
		view1Bottom = childHeight[1] + childHeight[2] + childHeight[3];
		int temp = (int) (yMove - statusHeight);
		Log.e("AAAAAAAAAAAAAAAAAAAAAa", temp + " " + childHeight[0]);
		if (temp < childHeight[0]) {

			Log.e("dealScroll", "view1");
			if (upOrDown == 0) {
				view1Bottom = view1Bottom + scrollSpeed;
			} else {
				view1Bottom = view1Bottom - scrollSpeed;
				
			}
			
		} else if (temp < (childHeight[0] + childHeight[1])
				&& temp > childHeight[0]) {

			Log.e("dealScroll", "view2");
			if (upOrDown == 0) {
			} else {
			}

		} else if (temp < (childHeight[0] + childHeight[1] + childHeight[2])
				&& temp > (childHeight[0] + childHeight[1])) {

			Log.e("dealScroll", "view3");
			if (upOrDown == 0) {
			} else {
			}

		} else if (temp < (childHeight[0] + childHeight[1] + childHeight[2] + childHeight[3])
				&& temp > (childHeight[0] + childHeight[1] + childHeight[2])) {

			Log.e("dealScroll", "view4");
			if (upOrDown == 0) {
			} else {
			}

		}
	}
}
