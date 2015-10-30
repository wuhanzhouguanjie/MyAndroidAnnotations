package com.example.ui;

import com.example.Util.PublicUtils;
import com.example.myandroidannotations.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class MountanScenceView extends View {

	private Path mMountLeft = new Path();
	private Paint mMountLeftPaint = new Paint();
	
	private Path mMountRight = new Path();
	private Paint mMountRightPaint = new Paint();
	
	private Paint mPointPaint = new Paint();
	private float mMoveFactor = 0;
	
	private int INVALIDATE_DURATION = 20;
	
	private int MIN_MOUNT_HIGHT = 600;
	
	private int tempMountHeight  ;
	
	private int viewHeight;
	
	private int viewWidth;

	public MountanScenceView(Context context) {
		super(context);
		init(context);
	}

	public MountanScenceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MountanScenceView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		
//		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//	    viewWidth = PublicUtils.dip2px(context, wm.getDefaultDisplay().getWidth());
	    viewHeight = PublicUtils.dip2px(context,300);
	    tempMountHeight = viewHeight- 150;
		mMountLeftPaint.setAntiAlias(true);// 设置画笔的锯齿效果
		mMountLeftPaint.setStyle(Paint.Style.FILL);// 充满
		
		mMountRightPaint.setAntiAlias(true);// 设置画笔的锯齿效果
		mMountRightPaint.setStyle(Paint.Style.FILL);// 充满

		mPointPaint.setAntiAlias(true);// 设置画笔的锯齿效果
		mPointPaint.setStrokeWidth(20);
		mPointPaint.setStyle(Paint.Style.FILL);// 充满
		
		updateMountainPath();
	}

	private void updateMountainPath() {
		//第一座山左边的山体
		mMountLeft.reset();
		mMountLeft.moveTo(0, viewHeight - 150);
		mMountLeft.lineTo(0, viewHeight);
		mMountLeft.lineTo(130, viewHeight);
		mMountLeft.lineTo(200, tempMountHeight);
		mMountLeft.close();
		Log.e("AAAAAAAAAAAAAAAAAAAAAAAAa", viewHeight + " " +  tempMountHeight);
		//第一座山右边的山体
		mMountRight.reset();
		mMountRight.moveTo(130, viewHeight);
		mMountRight.lineTo(450, viewHeight);
		mMountRight.lineTo(200, tempMountHeight);
		mMountRight.close();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		updateMountainPath();
//		canvas.drawColor(R.color.desert_background);
		mMountLeftPaint.setColor(getResources().getColor(R.color.desert_moun_left));
		mMountRightPaint.setColor(getResources().getColor(R.color.desert_moun_right));
		mPointPaint.setColor(getResources().getColor(R.color.white));
		canvas.drawPath(mMountLeft, mMountLeftPaint);
		canvas.drawPath(mMountRight, mMountRightPaint);
//		canvas.drawPoint(10, 200, mPointPaint);
//		canvas.drawPoint(100, 100, mPointPaint);
//		canvas.drawPoint(200, 50, mPointPaint);
//		canvas.drawPoint(200, 100, mPointPaint);
//		canvas.drawPoint(200, 200, mPointPaint);
//		canvas.drawPoint(100, 200, mPointPaint);
		canvas.save();
		if( tempMountHeight >  MIN_MOUNT_HIGHT ){
			postInvalidateDelayed(INVALIDATE_DURATION);
			tempMountHeight = tempMountHeight - 10;
		}
	}
}
