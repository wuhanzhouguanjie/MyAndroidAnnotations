package com.example.activity;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.example.myandroidannotations.R;
import android.app.Activity;
import android.view.WindowManager;
import android.widget.RelativeLayout;


@EActivity(R.layout.activity_test)
public class TestActivity extends Activity{
	
	@ViewById
	RelativeLayout background;
	
	@AfterViews
	void initView(){
//		WindowManager wm = this.getWindowManager();
//	    int width = wm.getDefaultDisplay().getWidth();
//	    int height = wm.getDefaultDisplay().getHeight();
//		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)background.getLayoutParams();
//		param.height = width;
//		background.setLayoutParams(param);
	}
	
}
