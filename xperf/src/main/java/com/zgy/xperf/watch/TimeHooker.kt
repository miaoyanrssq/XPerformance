package com.zgy.xperf.watch

import com.zgy.xperf.log.XLog
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
/**
* @author zhengy
* 版本：1.0
* 创建时间：2021/1/18 4:27 PM
* description: 方法耗时检测
*/
@Aspect
class TimeHooker {

    @Around("execution(@com.zgy.xperf.watch.TimeLog * *(..))")
    @Throws(Throwable::class)
    fun timeLog(joinPoint: ProceedingJoinPoint){
        val signature = joinPoint.signature
        val watch = StopWatch()
        watch.start()
        try {
            joinPoint.proceed()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()

        }
        watch.stop()
        XLog.d("${signature.toString()}  cost: ${watch.getDurationTime()} ms", 3)
    }
}