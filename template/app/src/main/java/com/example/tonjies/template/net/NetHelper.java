package com.example.tonjies.template.net;

import com.example.tonjies.template.net.bean.Student;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by 舍长 on 2019/1/20
 * describe:
 */
public class NetHelper {
    private Api api;

    public NetHelper() {
        this.api = RetrofitHelper.getInstance().getServer();
    }

    //开始请求
    public Observable<ResponseBody> post(Student student) {
        return api.post(student);
    }
}
