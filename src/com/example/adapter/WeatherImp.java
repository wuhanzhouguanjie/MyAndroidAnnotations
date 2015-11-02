package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;


@EBean
public class WeatherImp implements WeatherFinder{

	@Override
	public List<WeatherInfo> findAll() {
		// TODO Auto-generated method stub
		List<WeatherInfo> weatherList = new ArrayList<WeatherInfo>();
		weatherList.add(new WeatherInfo("18 C°", "晴天"));
		weatherList.add(new WeatherInfo("20 C°", "晴天"));
		weatherList.add(new WeatherInfo("22 C°", "晴天"));
		weatherList.add(new WeatherInfo("16 C°", "阴天"));
		return weatherList;
	}

}
