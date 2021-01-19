package com.zgy.xperf.block

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Printer

/**
 * @author zhengy
 * 版本：1.0
 * 创建时间：2021/1/19 11:50 AM
 * description: 在触发 ui 线程操作的时候，同时在后台线程启动一个延时的任务，到了时间，延时任务还没有被移除，
 *    说明 ui 线程 block 了，此时打印出 ui 线程的状态
 */
object BlockCheck {

    var INTERVAL = 100L
    var dumpInfoHandler: Handler? = null
    private val dumpThread = DumpThread("DumpThread")
    private val dumpRunnable: DumpRunnable = DumpRunnable()

    fun start() {
        dumpThread.start()
        Looper.getMainLooper().setMessageLogging(WatcherMainLooperPrinter())
    }

    private fun startDump() {
        dumpInfoHandler?.let {
            it.removeCallbacks(dumpRunnable)
            it.postDelayed(dumpRunnable, INTERVAL)
        }
    }

    private fun clearDump() {
        dumpInfoHandler?.let {
            it.removeCallbacks(dumpRunnable)
        }
    }


    /**
     * Looper 执行打印器
     */
    private class WatcherMainLooperPrinter : Printer {
        override fun println(x: String?) {
            x?.let {
                if (it.startsWith(">>>>>")) {
                    startDump()
                } else if (it.startsWith("<<<<<")) {
                    clearDump()
                }
            }
        }

    }

    /**
     * 伴随线程,打印现场信息
     */
    class DumpThread constructor(name: String) : Thread(name) {
        override fun run() {
            Looper.prepare()
            dumpInfoHandler = Handler(Looper.myLooper())
            Looper.loop()
            super.run()
        }
    }

    class DumpRunnable : Runnable {
        override fun run() {
            printer(Looper.getMainLooper().thread)
        }

    }

    fun printer(thread: Thread) {
        val stack = thread.stackTrace
        val builder = StringBuilder("Block msg\n")
        builder.append("---------------------------Block msg--------------------------------\n")
        stack.forEach {
            builder.append(it).append("\n")
        }
        builder.append("---------------------------Block msg end--------------------------------")
        Log.w("Block msg", builder.toString())
    }
}