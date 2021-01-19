package com.zgy.xperformance

import android.app.Application
import com.zgy.xperf.XPerf
import com.zgy.xperf.loginhooker.LoginManager

class XApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        XPerf.checkFPS(true, 10000L)
            .checkBlock(true, 100L)
            .setLoginManager(LoginManager())
            .build()
    }
}