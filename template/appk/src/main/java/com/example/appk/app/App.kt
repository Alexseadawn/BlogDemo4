package com.example.appk.app

import android.annotation.SuppressLint
import com.example.common.app.BaseApp

@SuppressLint("StaticFieldLeak")
/**
 * Created by 舍长 on 2019/1/19
 * describe:
 */
class App : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        //在这里进行第三方的初始化
        AppConfig.init()
    }
}