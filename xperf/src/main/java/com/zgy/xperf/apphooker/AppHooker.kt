package com.zgy.xperf.apphooker

import com.zgy.xperf.log.XLog
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
* @author zhengy
* 版本：1.0
* 创建时间：2021/1/18 5:56 PM
* description: application onCreate执行时间
*/
@Aspect
class AppHooker {

    @Around("execution(* android.app.Application+.onCreate(..))")
    @Throws(Throwable::class)
    fun getTime(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.signature
        val time = System.currentTimeMillis()
        try {
            joinPoint.proceed()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()

        }
        XLog.d("${signature.toString()}  cost: ${System.currentTimeMillis() - time} ms", 3)
    }



}