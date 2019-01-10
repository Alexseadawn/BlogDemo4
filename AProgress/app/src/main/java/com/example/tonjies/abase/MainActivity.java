package com.example.tonjies.abase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tonjies.abase.module.easy.EasyActivity;
import com.example.tonjies.abase.module.example.ExampleActivity;
import com.example.tonjies.abase.module.simple.SimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 舍长
 * describe:
 */
public class MainActivity extends AppCompatActivity {

    //简单的进度条示例
    @BindView(R.id.btn_01)
    Button btn01;

    //下方带文本的进度条示例
    @BindView(R.id.btn_02)
    Button btn02;

    //完整的案例
    @BindView(R.id.btn_03)
    Button btn03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_01, R.id.btn_02, R.id.btn_03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //简单的进度条示例
            case R.id.btn_01:
                startActivity(new Intent(this, EasyActivity.class));
                break;
            //下方带文本的进度条示例
            case R.id.btn_02:
                startActivity(new Intent(this, SimpleActivity.class));
                break;
            //完整的案例
            case R.id.btn_03:
                startActivity(new Intent(this, ExampleActivity.class));
                break;
        }
    }
}
