package com.example.tonjies.abase.util;

import android.graphics.Color;

/**
 * Created by 舍长 on 2019/1/14
 * describe:修改颜色透明度工具类
 */
public class AColor {
    /**
     * 修改颜色透明度
     * @param color
     * @param alpha
     * @return
     */
    public static int changeAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
