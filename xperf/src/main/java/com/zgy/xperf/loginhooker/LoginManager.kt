package com.zgy.xperf.loginhooker

import com.zgy.xperf.log.XLog

open class LoginManager {

    /**
     * 未登陆后续操作
     */
    fun showTips(){
        XLog.e("还没有登陆", 6)
    }

    /**
     * 是否登陆判断标准
     */
    fun isLogin(): Boolean{
        return false
    }
}