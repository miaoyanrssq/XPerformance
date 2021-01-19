package com.zgy.xperf.activityhooker

import com.zgy.xperf.log.XLog
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before


@Aspect
class ActivityHooker {

    val record: ActivityRecord = ActivityRecord()

    /**
     * oncreate执行时间
     */
    @Around("execution(* androidx.appcompat.app.AppCompatActivity+.onCreate(..))")
    @Throws(Throwable::class)
    fun getonCreateTime(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.signature
        val time = System.currentTimeMillis()
        try {
            joinPoint.proceed()
        } catch (throwable: Throwable) {
            throwable.printStackTrace()

        }
        XLog.d("${signature.toString()}  cost: ${System.currentTimeMillis() - time} ms", 3)
    }


    /**
     * onCreate开始时间，和下面一个方法配合使用，统计页面加载时间
     */
    @Before("execution(* androidx.appcompat.app.AppCompatActivity+.onCreate(..))")
    @Throws(Throwable::class)
    fun startTime(joinPoint: JoinPoint){
        record.isNewCreate = true
        record.mOnCreateTime = System.currentTimeMillis()


    }

    /**
     * 页面展示时间
     */
    @After("execution(* androidx.appcompat.app.AppCompatActivity+.onWindowFocusChanged(..))")
    @Throws(Throwable::class)
    fun showTime(joinPoint: JoinPoint){
        val signature = joinPoint.signature
        if(!record.isNewCreate){
            return
        }
        record.isNewCreate = false
        record.mOnWindowsFocusChangedTime = System.currentTimeMillis()
        XLog.d("${signature.toString()}  页面加载cost: ${record.mOnWindowsFocusChangedTime - record.mOnCreateTime} ms", 3)
    }
}