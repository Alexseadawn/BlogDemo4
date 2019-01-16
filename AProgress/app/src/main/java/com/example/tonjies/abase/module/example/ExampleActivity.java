package com.example.tonjies.abase.module.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tonjies.abase.R;
import com.example.tonjies.abase.view.ProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 舍长
 * describe:完整的使用案例
 */
public class ExampleActivity extends AppCompatActivity {

    @BindView(R.id.progressView)
    ProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        ButterKnife.bind(this);
        String[] s={"体验很好","体验不错","体验还行","体验一般"};
        progressView.setEvalustes(s);
    }
}
