package com.example.tonjies.upush.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * 进行友盟推送SDK的初始化
 */
public class UmengConfig {
    private static UmengConfig instance;

    private UmengConfig() {
    }

    public static UmengConfig getInstance() {
        if (instance == null) {
            instance = new UmengConfig();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Application application) {
        //PushSDK初始化(如使用推送SDK，必须调用此方法)
        //初始化友盟通用库，如需使用权限菜单配置好的appley和channel值，init调用中，请传入null
        /**
         * 参数
         * 1，Context对象
         * 2，应用Appkey
         * 3，渠道号
         * 4，类型，这里是手机类型
         * 4，推送Umeng Message Secret
         * 这里要注意的是Appkey和Secret一定要对，这两个信息可以在友盟的U-push后台管理页面的应用管理-应用信息里面找到
         */
        UMConfigure.init(application, "5c2d51f0b465f55e2300058b", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "b6bf729abc5bfdab85b8083da628a286");
        final PushAgent mPushAgent = PushAgent.getInstance(application);
        //注册
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志，如果我们可以在Log里面输出token，说明我们的初始化是成功了
                Log.d("umengConfig", "注册成功:deviceToken:" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("umengConfig", "注册失败;" + "s:" + s + " s1" + s1);
            }
        });
    }
}