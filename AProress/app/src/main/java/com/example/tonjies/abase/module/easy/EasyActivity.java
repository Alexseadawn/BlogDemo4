package com.example.tonjies.abase.module.easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.tonjies.abase.R;
import com.example.tonjies.abase.util.L;
import com.example.tonjies.abase.view.EasyProgress;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 舍长
 * describe:最简单的进度条
 */
public class EasyActivity extends AppCompatActivity {

    //实
    @BindView(R.id.easyProgress)
    EasyProgress easyProgress;

    //当前进度条的百分比文本
    @BindView(R.id.tvCurrentProgress)
    TextView tvCurrentProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
        tvCurrentProgress.setVisibility(View.VISIBLE);
        easyProgress.setOnProgressListener(new EasyProgress.OnProgressListener() {
            @Override
            public void onSelect(int progress) {
                tvCurrentProgress.setText(progress + "/100");
//                L.d("progress:"+progress);
            }
        });
    }

}
