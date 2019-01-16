package com.example.tonjies.abase.module.easy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.tonjies.abase.R;
import com.example.tonjies.abase.view.EasyProgress;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 舍长
 * describe:最简单的进度条
 */
public class EasyActivity extends AppCompatActivity {

    //进度条
    @BindView(R.id.easyProgress)
    EasyProgress easyProgress;

    //当前进度条的百分比文本
    @BindView(R.id.tvCurrentProgress)
    TextView tvCurrentProgress;

    //增加进度按钮
    @BindView(R.id.faButton)
    FloatingActionButton faButton;

    //每次累加的进度
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
        tvCurrentProgress.setVisibility(View.VISIBLE);
        faButton.setVisibility(View.VISIBLE);
        //监听数值的变化
        easyProgress.setOnProgressListener(new EasyProgress.OnProgressListener() {
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
