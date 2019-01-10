package com.example.tonjies.upush.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.message.PushAgent;

/**
 * Created by 舍长
 * describe:基础Activity
 */
public class ASuperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PushAgent.getInstance(this).onAppStart();
        super.onCreate(savedInstanceState);
    }
}
