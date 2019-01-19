package com.example.appk.module.refrence.net

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.appk.R
import com.example.appk.net.bean.Student
import com.example.common.util.L
import com.example.tonjies.mvp.net.Api
import com.example.tonjies.weatherx.net.RetrofitFactory
import kotlinx.android.synthetic.main.activity_net.*
import org.jetbrains.anko.toast
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by 舍长
 * describe:网络请求示例
 */
class NetActivity : AppCompatActivity() {

    //声明Api接口api
    private var api: Api? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        api = RetrofitFactory.instance.create(Api::class.java);
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = item.itemId
        when (i) {
            //简单的Get请求，这里用到的依赖比较多，注意去Common看看依赖时候都已经调价
            R.id.one -> {
                //进行网络请求
                api!!.getData()
                        .observeOn(AndroidSchedulers.mainThread()) //
                        .subscribeOn(rx.schedulers.Schedulers.io())
                        .subscribe(object : rx.Observer<Student> {
                            override fun onError(e: Throwable) {
                                Log.d("tonjies", e.message)
                            }

                            override fun onNext(t: Student) {
                                val studentName: String = t.name //学生姓名
                                val studentAge: String = t.age //学生年龄
                                L.d("studentName:$studentName studentAge:$studentAge")
                                tvNetReturn.text="$t"
                            }

                            override fun onCompleted() {
                            }
                        })
            }
            //
            R.id.two -> {
                toast("two")
            }
            //
            R.id.three -> {
                toast("three")
            }
            //
            R.id.four -> {
                toast("four")
            }
            //
            R.id.five -> {
                toast("five")
            }
            //
            R.id.six -> {
                toast("six")
            }
            //
            R.id.seven -> {
                toast("seven")
            }
            //
            R.id.eight -> {
                toast("eight")
            }
        }
        return true
    }
}
