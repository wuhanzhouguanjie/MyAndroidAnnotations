package com.example.activity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.res.StringRes;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Util.PinyingUtil;
import com.example.application.BaseApplication;
import com.example.myandroidannotations.R;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.activity_weather)

public class WeatherActivity extends Activity{

	@ViewById
	EditText et;
	
	//整体信息线性布局
	@ViewById
	LinearLayout lin;
	
	//城市
	@ViewById
	TextView city;
	
	//体感温度
	@ViewById
	TextView fl;
	
	//天气状况
	@ViewById
	TextView cond;
	
	@ViewById
	TextView pm25,qlty,drsg,sport;
	
	//穿衣建议，运动建议父布局
	@ViewById
	LinearLayout drsgLin,sportLin;
	
	@StringRes(R.string.search_city_failed)
	String searchCityFailed;
	
	@StringRes(R.string.search_failed)
	String searchFailed;
	
	@Click
	void btn() {
		setProgressBarIndeterminateVisibility(true);
		someBackgroundWork("weather", 1);

	}
	
	@Background
	void someBackgroundWork(String aParam, long anotherParam){
		String result = BaseApplication.client.getWeatherInfo
				(PinyingUtil.hanziToPinyin(et.getText().toString(),""));
		updateUi(result);
	}
	
	@UiThread //UI线程
    void updateUi(String result) {
        setProgressBarIndeterminateVisibility(false);
        if( result == null ){
        	Toast.makeText(getApplicationContext(), searchFailed, Toast.LENGTH_SHORT).show();
        	return;
        }
        lin.setVisibility(View.VISIBLE);
        saxJson(result);
		Log.e("城市", PinyingUtil.hanziToPinyin(et.getText().toString(),""));
    }
	
	private void saxJson(String result){
		
		try {
			JSONObject json = new JSONObject(result);
			JSONObject jsonInfo = (JSONObject) json.getJSONArray("HeWeather data service 3.0").get(0);
			if( jsonInfo.getString("status").equals("ok") ){
				city.setText(jsonInfo.getJSONObject("basic").getString("city"));
				fl.setText(jsonInfo.getJSONObject("now").getString("fl"));
				cond.setText(jsonInfo.getJSONObject("now").getJSONObject("cond").getString("txt"));
				pm25.setText(jsonInfo.getJSONObject("aqi").getJSONObject("city").getString("pm25"));
				qlty.setText(jsonInfo.getJSONObject("aqi").getJSONObject("city").getString("qlty"));
				
				//国外城市没有,因此做异常判断
				try{
					drsgLin.setVisibility(View.VISIBLE);
					sportLin.setVisibility(View.VISIBLE);
					drsg.setText(jsonInfo.getJSONObject("suggestion").getJSONObject("drsg").getString("brf") 
							+ "\n"
							+ jsonInfo.getJSONObject("suggestion").getJSONObject("drsg").getString("txt"));
					
					sport.setText(jsonInfo.getJSONObject("suggestion").getJSONObject("sport").getString("brf") 
							+ "\n"
							+ jsonInfo.getJSONObject("suggestion").getJSONObject("sport").getString("txt"));
				}catch(Exception e){
					drsgLin.setVisibility(View.GONE);
					sportLin.setVisibility(View.GONE);
				}
				
			}else{
				Toast.makeText(getApplicationContext(), searchCityFailed, Toast.LENGTH_SHORT).show();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
