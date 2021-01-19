package com.zgy.xperf.fps

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.Choreographer
import com.zgy.xperf.log.XLog

/**
* @author zhengy
* 版本：1.0
* 创建时间：2021/1/19 11:16 AM
* description: 利用 Choreographer 向系统注册一个 FrameCallback
 * 在 FrameCallback 的回调方法里面计数，这个回调方法调用一次就表示绘制了一帧。
 * 同时，每隔一段时间向 main thread 放一个 runnable ，这个 runnable 做的事情
 * 就是统计两次 run 方法之间 FrameCallback 的回调方法调用了多少次，也就是绘制
 * 了多少帧，通过两次 run 方法之间绘制的帧数就可以计算出来 app 的帧率。
*/
object FpsCheck{
    var INTERVAL = 5000L
    lateinit var handler: Handler
    private val frameRunnable = FrameRunnable()

    fun start(){
        handler = Handler(Looper.getMainLooper())
        handler.post(frameRunnable)
        Choreographer.getInstance().postFrameCallback(frameRunnable)
    }

    class FrameRunnable : Runnable, Choreographer.FrameCallback{

        var time = 0L
        var count = 0

        override fun doFrame(frameTimeNanos: Long) {
            count ++
            Choreographer.getInstance().postFrameCallback(this)
        }

        override fun run() {
            val curTime = SystemClock.elapsedRealtime()
            if(time != 0L){
                val fps = 1000f * count / (curTime - time) + 0.5f
                if(fps <= 50){
                    XLog.w("APP FPS is $fps HZ")
                }else{
                    XLog.d("APP FPS is $fps HZ")
                }
            }
            count = 0
            time = curTime
            handler.postDelayed(this, INTERVAL)
        }
    }

}