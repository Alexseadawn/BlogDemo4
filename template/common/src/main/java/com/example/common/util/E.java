package com.example.common.util;

import android.widget.EditText;

/**
 * Created by 舍长 on 2018/12/14
 * describe:获取EeitView的值
 */
public class E {
    public static String g(EditText editText) {
        final String trim = editText.getText().toString().trim();
        return trim;
    }
}
