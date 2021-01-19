package com.zgy.xperf.clickhooker

import android.util.Log
import android.view.View
import com.zgy.xperf.R
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
/**
* @author zhengy
* 版本：1.0
* 创建时间：2021/1/18 3:21 PM
* description: 快速点击事件检测
*/
@Aspect
class ClickHooker {

    val TIME_TAG = R.id.click_time
    val MIN_CLICK_DELAY_TIME = 500


    @Around("execution(* android.view.View.OnClickListener+.onClick(..))")
    @Throws(Throwable::class)
    fun clickMethod(joinPoint: ProceedingJoinPoint){
        var view: View? = null
        joinPoint.args.forEach {
            if(it is View) view = it
            return@forEach
        }
        view?.let {
            var tag = it.getTag(TIME_TAG)
            var last = if(null != tag) tag as Long else 0L
            var current = System.currentTimeMillis()
            if(current - last > MIN_CLICK_DELAY_TIME){
                it.setTag(TIME_TAG, current)
                joinPoint.proceed()
            }else{
               Log.d("click", "it's too short in double click")
            }
        }
    }

}