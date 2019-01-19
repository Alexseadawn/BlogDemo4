package com.example.tonjies.template.net;

import com.example.tonjies.template.net.bean.Student;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 舍长 on 2018/12/12
 * describe:
 */
public interface Api {

    String baseUrl = "https://httpbin.org/";

    //增加一行数据
    @Headers({
            "Content-Type:application/json"
    })
    @POST("/post")
    Observable<ResponseBody> post(@Body Student student);
}
