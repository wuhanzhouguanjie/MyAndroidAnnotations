package com.example.application;

import org.androidannotations.annotations.EApplication;

import com.example.net.ClientNetwork;

import android.app.Application;

@EApplication
public class BaseApplication extends Application{
	
	public static ClientNetwork client;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}
	
	private void init(){
		client = new ClientNetwork(getApplicationContext());
	}
}
