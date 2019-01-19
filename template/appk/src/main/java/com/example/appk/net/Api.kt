package com.example.tonjies.mvp.net

import com.example.appk.net.bean.Student
import retrofit2.http.GET
import rx.Observable

interface Api {
    //获取学生信息
    //查询天气信息
    @GET("2872/student")//接口的后段部分
    fun getData(): Observable<Student>
}