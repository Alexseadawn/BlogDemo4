package com.example.tonjies.upush

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.tonjies.upush.base.ASuperActivity
import com.umeng.message.PushAgent

class MainActivity : ASuperActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PushAgent.getInstance(this).onAppStart()
    }
}
