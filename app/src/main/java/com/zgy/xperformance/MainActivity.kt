package com.zgy.xperformance

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zgy.xperf.loginhooker.CheckLogin
import com.zgy.xperf.watch.TimeLog
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.hello).setOnClickListener {
            testLogin()
            testTimeLog()
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