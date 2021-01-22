package com.zgy.xperformance

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zgy.test.TestActivity
import com.zgy.xperf.loginhooker.CheckLogin
import com.zgy.xperf.watch.TimeLog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<TextView>(R.id.hello).setOnClickListener {
//            startActivity(Intent(this, TestActivity::class.java))
////            testLogin()
////            testTimeLog()
//        }
        hello.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }

    @CheckLogin
    fun testLogin(){

    }

    @TimeLog
    fun testTimeLog(){
        sleep(2000)
    }
}