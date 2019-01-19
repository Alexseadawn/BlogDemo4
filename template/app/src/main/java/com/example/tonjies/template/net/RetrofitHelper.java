package com.example.tonjies.template.net;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 舍长 on 2019/1/20
 * describe:
 */
public class RetrofitHelper {

    GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper apiHelper = null;
    private Retrofit retrofit = null;

    public static RetrofitHelper getInstance() {
        if (apiHelper == null) {
            apiHelper = new RetrofitHelper();
        }
        return apiHelper;
    }

    private RetrofitHelper() {
        init();
    }

    private void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Api getServer() {

        return retrofit.create(Api.class);
    }

    public OkHttpClient getOkHttpClient() {

        // TODO: 创建OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder
                // 连接超时设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时设置
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
}
