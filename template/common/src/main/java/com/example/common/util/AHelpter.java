package com.example.common.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.common.app.BaseApp;

/**
 * Created by 舍长 on 2018/12/14
 * describe:吐司工具类
 */
public class AHelpter {
    /**
     * 弹出短吐司
     */
    public static void s(Object object) {
        //这里的参数为object而不是String的目的是为了能传入更多类型的数据
        //而不管传入什么类型，我们都把它转成String类型，这样就使用起来就比较方便了，不用再进行toString操作
        //而我们如果传进来，不是数据类型，而是自己定义的类，就会打印出对象的toString方法
        String s = object.toString();
        Toast.makeText(BaseApp.getContext(), "" + s, Toast.LENGTH_SHORT).show();
    }
    /**
     * 判断String字符串是否为空,如果不是空返回true，如果是空返回false
     */
    public static Boolean e(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断两个String字符串是否有一个为空,如果不是空返回true，如果是空返回false
     */
    public static Boolean e(String msg1, String msg2) {
        if (TextUtils.isEmpty(msg1) || TextUtils.isEmpty(msg2)) {
            return true;
        } else {
            return false;
        }
    }

}
