package com.example.tonjies.abase.module.tprogress;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.tonjies.abase.R;
import com.example.tonjies.abase.module.refrence.TProgress;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 舍长
 * describe:底部带有文字的进度条
 */
public class TProgressActivity extends AppCompatActivity {


    //当前进度条的百分比文本
    @BindView(R.id.tvCurrentProgress)
    TextView tvCurrentProgress;

    //增加进度按钮
    @BindView(R.id.faButton)
    FloatingActionButton faButton;

    //带文字进度条
    @BindView(R.id.easyProgress)
    TProgress easyProgress;

    //每次累加的进度
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);
        tvCurrentProgress.setVisibility(View.VISIBLE);
        faButton.setVisibility(View.VISIBLE);
        easyProgress.setEvaluates(new String[]{"效果很差", "效果还行","效果很棒"});
        //监听数值的变化
        easyProgress.setOnProgressListener(new TProgress.OnProgressListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSelect(int progress) {
                tvCurrentProgress.setText(progress + "/100");
//                L.d("progress:"+progress);
            }
        });
        //设置进度值
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress < 100) {
                    progress += 10;
                    easyProgress.setProgress(progress);
                } else {
                    progress = 0;
                }
            }
        });
    }
}
