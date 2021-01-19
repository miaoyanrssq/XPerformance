package com.zgy.xperf.watch

import java.util.concurrent.TimeUnit
/**
* @author zhengy
* 版本：1.0
* 创建时间：2021/1/18 2:44 PM
* description: 事件统计类
*/
class StopWatch {
    var startTime = 0L
    var endTime = 0L
    var duration = 0L

    fun reset(){
        startTime = 0L
        endTime = 0L
        duration = 0
    }

    fun start(){
        reset()
        startTime = System.nanoTime()
    }

    fun stop(){
        if(startTime != 0L){
            endTime = System.nanoTime()
            duration = endTime - startTime
        }else{
            reset()
        }
    }

    fun getDurationTime() = if(duration != 0L) TimeUnit.NANOSECONDS.toMillis(duration) else 0L
}