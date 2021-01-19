package com.zgy.xperf.loginhooker

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
object LoginHooker {
    var manager: LoginManager? = null

    fun setLoginManager( loginManager: LoginManager){
        manager = loginManager
    }

    @Around("execution(@com.zgy.xperf.loginhooker.CheckLogin * *(..))")
    @Throws(Throwable::class)
    fun loginCheck(joinPoint: ProceedingJoinPoint){
        if(!isLogin()){
            showTips()
            return
        }
        joinPoint.proceed()
    }

    private fun showTips(){
        manager?.showTips()
    }

    private fun isLogin(): Boolean{
        return manager?.isLogin() ?: false
    }
}