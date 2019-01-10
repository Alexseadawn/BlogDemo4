package com.example.tonjies.abase.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by 舍长 on 2019/1/8
 * describe:
 */
public class App extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public static Context getContext() {
        return context;
    }
}
