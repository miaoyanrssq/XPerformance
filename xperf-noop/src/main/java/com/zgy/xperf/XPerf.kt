package com.zgy.xperf

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
    }

    /**
     * fps开关
     */
    fun checkFPS(check: Boolean, interval: Long) = apply {

    }

    /**
     * 登陆管理类，用户可自定义未登陆拦截操作
     */
    fun setLoginManager(manager: LoginManager) = apply {
    }

    fun build() {

    }


}