package com.example.tonjies.template.module.refrence.net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.util.L;
import com.example.tonjies.template.R;
import com.example.tonjies.template.net.NetHelper;
import com.example.tonjies.template.net.bean.Student;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by 舍长
 * describe:网络请求示例，未封装，仅仅是为了示范网络请求的基本使用
 */
public class NetActivity extends AppCompatActivity {

    //显示返回的数据
    @BindView(R.id.tvNetReturn)
    TextView tvNetReturn;

    //这里Helper相当于我们Mvp模式的Model层
    //可以查看我的这篇教程，https://www.jianshu.com/p/d00f912d81df
    private NetHelper netHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        ButterKnife.bind(this);
        netHelper = new NetHelper();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            //post请求示例
            case R.id.one:
                post();
                Toast.makeText(this, "one", Toast.LENGTH_SHORT).show();
                break;
            case R.id.two:
                Toast.makeText(this, "two", Toast.LENGTH_SHORT).show();
                break;
            case R.id.three:
                Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                break;
            case R.id.four:
                Toast.makeText(this, "four", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void post() {
        Student student = new Student("tonjies", 21);
        netHelper.post(student)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            L.d("string:"+string);
                            tvNetReturn.setText(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
