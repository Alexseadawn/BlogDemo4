package com.example.common.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by 舍长 on 2018/12/14
 * describe:项目基础Application
 */
public class BaseApp extends Application {

    private static Context context;

    //我们也可以在这里初始化一些项目通用的东西
    //比如查看数据库数据文件的Stetho
    //这里就不演示了，详细集成可以查看我的另一篇文章
    //https://www.jianshu.com/p/6a3b0ae4aeb4
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    //创建一个静态的方法，以便获取context对象
    public static Context getContext() {
        return context;
    }
}
