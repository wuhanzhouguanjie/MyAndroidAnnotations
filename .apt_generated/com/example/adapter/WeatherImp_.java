//
// DO NOT EDIT THIS FILE.Generated using AndroidAnnotations 3.3.2.
//  You can create a larger work that contains this file and distribute that work under terms of your choice.
//


package com.example.adapter;

import android.content.Context;

public final class WeatherImp_
    extends WeatherImp
{

    private Context context_;

    private WeatherImp_(Context context) {
        context_ = context;
        init_();
    }

    public static WeatherImp_ getInstance_(Context context) {
        return new WeatherImp_(context);
    }

    private void init_() {
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
