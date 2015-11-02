package com.example.adapter;

import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

@EBean
public class WeatherAdapter extends BaseAdapter{
	
	List<WeatherInfo> mInfo;
	
	@RootContext
    Context context;

	@Bean(WeatherImp.class)
	WeatherFinder weatherFinder;
	
	
	@AfterInject
    void initAdapter() {
		mInfo = weatherFinder.findAll();
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		WeatherItemView weatherItemView;
		if( convertView == null ){
			weatherItemView = WeatherItemView_.build(context);
		}else{
			weatherItemView = (WeatherItemView)convertView;
		}
		
		weatherItemView.bind((WeatherInfo)getItem(position));
		
		return weatherItemView;
	}

}
