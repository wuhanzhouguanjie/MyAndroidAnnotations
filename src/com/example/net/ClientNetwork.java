package com.example.net;

import java.io.IOException;

import com.example.myandroidannotations.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.content.Context;
import android.util.Log;

public class ClientNetwork implements ClienNetworkInterface {

	private OkHttpClient mClient;
	private Context mContext;

	public ClientNetwork(Context context) {
		mClient = new OkHttpClient();
		mContext = context;
	}

	public void asynGetRequest(String url,String key) throws Exception {
		
		Request request = new Request.Builder().
				url(url)
				.addHeader("apikey", key)
				.build();
		
		mClient.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				if (!response.isSuccessful())
					throw new IOException("Unexpected code " + response);

				Headers responseHeaders = response.headers();
				for (int i = 0; i < responseHeaders.size(); i++) {
					System.out.println(responseHeaders.name(i) + ": "
							+ responseHeaders.value(i));
				}

				System.out.println(response.body().string());
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				arg1.printStackTrace();
			}
		});
	}

	
	public String syncGetRequest(String url,String key) throws Exception {
		
		Request request = new Request.Builder()
	    .url(url)
	    .addHeader("apikey", key)
	    .build();

		
		Response response = mClient.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		
		return response.body().string();
	}
	
	@Override
	public String getWeatherInfo(String city) {
		// TODO Auto-generated method stub
		
		String url = mContext.getString(R.string.weather_url);
		url = url + "?city=" + city; 
		String key = mContext.getString(R.string.weather_apikey);
		
		String result = null;
		try {
			result = syncGetRequest(url,key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = null;
			e.printStackTrace();
		}
		Log.e("getWeatherInfo", result);
		return result;
	}

}
