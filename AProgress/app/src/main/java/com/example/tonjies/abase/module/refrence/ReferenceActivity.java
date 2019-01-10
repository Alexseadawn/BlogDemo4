package com.example.tonjies.abase.module.refrence;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tonjies.abase.R;
import com.example.tonjies.abase.view.ProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 舍长
 * describe:参考Activity
 */
public class ReferenceActivity extends AppCompatActivity {


    @BindView(R.id.progressView)
    ProgressView progressView;
    @BindView(R.id.tv)
    TextView tv;
    private String[] evaluate = {"体验很好", "体验还行", "体验一般", "体验很差"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrence);
        ButterKnife.bind(this);
        progressView.setEvalustes(evaluate);

    }
}
