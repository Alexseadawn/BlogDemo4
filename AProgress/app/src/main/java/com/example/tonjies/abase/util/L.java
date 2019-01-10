package com.example.tonjies.abase.util;

import android.util.Log;

/**
 * Created by Tulin_TonJie on 2018/2/10.
 */

public class L {
    //开关
    public static final boolean DEBUG = true;

    //TAG,你看到的世界是怎么样的呢
    public static final String TAG = "helloWorld";

    //5个等级 DIWEF

    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text+"");
        }
    }

    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text+"");
        }
    }

    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text+"");
        }
    }

    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text+"");
        }
    }
}
