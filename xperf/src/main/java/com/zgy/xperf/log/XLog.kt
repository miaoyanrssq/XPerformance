package com.zgy.xperf.log

import com.orhanobut.logger.Logger
/**
* @author zhengy
* 版本：1.0
* 创建时间：2021/1/15 3:58 PM
* description: 日志管理
*/
object XLog {

    private var TAG = "LOG"

    fun setTAG(tag: String){
        TAG = tag
    }

    /**
     * level表示函数嵌套等级，此处2默认为调用打印函数的地方，如果使用aop，则需要配置为3才能看到切面的地方日志
     */
    private fun formatMsg(msg: String, level: Int = 2): String{
        val size = Exception().stackTrace.size
        val traceElement = Exception().stackTrace[if(level >= size) size -1 else level]
        return "${traceElement.fileName}   (${traceElement.lineNumber})   ${traceElement.methodName}\nmsg:$msg"
    }

    @Synchronized
    fun v(msg: String, level: Int = 2) {
        Logger.t(TAG).i(formatMsg(msg, level))
    }

    @Synchronized
    fun d(msg: String, level: Int = 2) {
        Logger.t(TAG).d(formatMsg(msg, level))
    }

    @Synchronized
    fun i(msg: String, level: Int = 2) {
        Logger.t(TAG).i(formatMsg(msg, level))
    }

    @Synchronized
    fun w(msg: String, level: Int = 2) {
        Logger.t(TAG).w(formatMsg(msg, level))
    }

    @Synchronized
    fun e(msg: String, level: Int = 2) {
        Logger.t(TAG).e(formatMsg(msg, level))
    }

}