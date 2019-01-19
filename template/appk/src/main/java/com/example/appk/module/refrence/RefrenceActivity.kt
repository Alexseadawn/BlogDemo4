package com.example.appk.module.refrence

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.appk.R
import org.jetbrains.anko.toast

/**
 * Created by 舍长
 * describe:kotlin参考Activitu
 */
class RefrenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refrence)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = item.itemId
        when (i) {
            //
            R.id.one -> {
                toast("one")
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
