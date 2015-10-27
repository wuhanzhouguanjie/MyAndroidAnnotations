package com.example.net;

import android.content.Context;

public interface ClienNetworkInterface {
	
	/**
	 * 获取天气信息
	 * 
	 * @param city 城市名
	 * 
	 */
	public String getWeatherInfo(String city);
}
