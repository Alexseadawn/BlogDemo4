package com.example.common.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by 舍长 on 2018/2/22.
 * 描述:字体设置类
 * 引用至:https://www.jianshu.com/p/6784800b5cc9
 */

public class FontSetting {
    //设置字体
    public static void setFont(Context context, TextView textView, String fontsPath) {
        try {
            Typeface fromAsset = Typeface.createFromAsset(context.getAssets(), fontsPath);
            textView.setTypeface(fromAsset);
        } catch (Exception e) {
            L.d("资源文件错误");
        }
    }
}
