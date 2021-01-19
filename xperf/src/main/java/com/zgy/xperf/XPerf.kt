package com.zgy.xperf

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.zgy.xperf.block.BlockCheck
import com.zgy.xperf.fps.FpsCheck
import com.zgy.xperf.loginhooker.LoginHooker
import com.zgy.xperf.loginhooker.LoginManager

/**
 * @author zhengy
 * 版本：1.0
 * 创建时间：2021/1/15 3:58 PM
 * description: 性能检测入口
 */
object XPerf {

    private var checkUI: Boolean = false
    private var checkFps: Boolean = false

    /**
     * block开关
     */
    fun checkBlock(check: Boolean, interval: Long) = apply {
        if (interval >= 100L) {
            BlockCheck.INTERVAL = interval
        }
        checkUI = check
    }

    /**
     * fps开关
     */
    fun checkFPS(check: Boolean, interval: Long) = apply {
        if (interval >= 1000L) {
            FpsCheck.INTERVAL = interval
        }
        checkFps = check

    }

    /**
     * 登陆管理类，用户可自定义未登陆拦截操作
     */
    fun setLoginManager(manager: LoginManager) = apply {
        LoginHooker.setLoginManager(manager)
    }

    fun build() {
        Logger.addLogAdapter(
            AndroidLogAdapter(
                PrettyFormatStrategy.newBuilder().tag("XPerf").methodCount(0).build()
            )
        )

        if (checkFps) FpsCheck.start()
        if (checkUI) BlockCheck.start()

    }


}