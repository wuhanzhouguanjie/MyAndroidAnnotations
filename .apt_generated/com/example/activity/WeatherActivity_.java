//
// DO NOT EDIT THIS FILE.Generated using AndroidAnnotations 3.3.2.
//  You can create a larger work that contains this file and distribute that work under terms of your choice.
//


package com.example.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.myandroidannotations.R.id;
import com.example.myandroidannotations.R.layout;
import com.example.myandroidannotations.R.string;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class WeatherActivity_
    extends WeatherActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_weather);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        Resources resources_ = this.getResources();
        searchCityFailed = resources_.getString(string.search_city_failed);
        searchFailed = resources_.getString(string.search_failed);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static WeatherActivity_.IntentBuilder_ intent(Context context) {
        return new WeatherActivity_.IntentBuilder_(context);
    }

    public static WeatherActivity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new WeatherActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        lin = ((LinearLayout) hasViews.findViewById(id.lin));
        cond = ((TextView) hasViews.findViewById(id.cond));
        et = ((EditText) hasViews.findViewById(id.et));
        pm25Lin = ((LinearLayout) hasViews.findViewById(id.pm25Lin));
        fl = ((TextView) hasViews.findViewById(id.fl));
        pm25 = ((TextView) hasViews.findViewById(id.pm25));
        qltyLin = ((LinearLayout) hasViews.findViewById(id.qltyLin));
        sportLin = ((LinearLayout) hasViews.findViewById(id.sportLin));
        qlty = ((TextView) hasViews.findViewById(id.qlty));
        drsg = ((TextView) hasViews.findViewById(id.drsg));
        drsgLin = ((LinearLayout) hasViews.findViewById(id.drsgLin));
        sport = ((TextView) hasViews.findViewById(id.sport));
        city = ((TextView) hasViews.findViewById(id.city));
        {
            View view = hasViews.findViewById(id.btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        WeatherActivity_.this.btn();
                    }

                }
                );
            }
        }
    }

    @Override
    public void updateUi(final String result) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                WeatherActivity_.super.updateUi(result);
            }

        }
        );
    }

    @Override
    public void someBackgroundWork(final String aParam, final long anotherParam) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    WeatherActivity_.super.someBackgroundWork(aParam, anotherParam);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<WeatherActivity_.IntentBuilder_>
    {

        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, WeatherActivity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), WeatherActivity_.class);
            fragmentSupport_ = fragment;
        }

        @Override
        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent, requestCode);
            } else {
                if (context instanceof Activity) {
                    Activity activity = ((Activity) context);
                    ActivityCompat.startActivityForResult(activity, intent, requestCode, lastOptions);
                } else {
                    context.startActivity(intent);
                }
            }
        }

    }

}