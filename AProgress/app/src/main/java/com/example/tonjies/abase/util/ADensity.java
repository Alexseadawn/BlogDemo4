package com.example.tonjies.abase.util;

import android.content.Context;

import com.example.tonjies.abase.app.App;

/**
 * Created by 舍长 on 2019/1/10
 * describe:
 */
public class ADensity {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        float scale = 1;
        scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    // 将px值转换为sp值
    public static int px2sp(float pxValue) {
        final float fontScale = App.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
