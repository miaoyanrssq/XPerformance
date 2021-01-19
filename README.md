# android 性能检测工具

## 功能

* 统计Application onCreate()执行时间
* 统计Activity onCreate()执行时间
* 统计页面加载时间
* 连续多次点击检测
* 未登陆检测
* 自定义函数耗时检测
* FPS检测
* block卡顿检测

## 配置

```groovy
//项目build.gradle中
classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.8'

//module 的build.gradle中
apply plugin: 'android-aspectjx'
aspectjx{
    //包名，如果不配置，则匹配所有
    include 'com.zgy'
    //剔除某些包
    exclude 'com.zgy'
}

//依赖aar
implementation 'com.zgy.xperf:xperf:0.0.1'
//如果只需要在debug环境中使用，则依赖如下
debugImplementation 'com.zgy.xperf:xperf:0.0.1'
releaseImplementation 'com.zgy.xperf:xperf-noop:0.0.1'
```

## 使用

```kotlin
XPerf.checkFPS(true, 10000L)//fps打印间隔，时间越长，可能被平均的越厉害，建议1000-2000ms
    .checkBlock(true, 100L)//卡顿阈值
    .setLoginManager(LoginManager())//自定义未登陆检测响应
    .build()
```

其中：

* 统计Application onCreate()执行时间
* 统计Activity onCreate()执行时间
* 统计页面加载时间
* 连续多次点击检测

是自动检测的，配置好之后即可使用

```verilog
┌────────────────────────────────────────────────────────────────────────────────────────────────────────────
│ Thread: main
├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
│ MainActivity.kt   (22)   onWindowFocusChanged
│ msg:void com.zgy.xperformance.MainActivity.onWindowFocusChanged(boolean)  页面加载cost: 143 ms
└────────────────────────────────────────────────────────────────────────────────────────────────────────────
```



### 未登陆检测

可以扩展LoginManager

```kotlin
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

```

然后在需要检测未登陆的方法添加注释：

```kotlin
@CheckLogin
fun testLogin(){

}
```

### 自定义函数耗时检测

```kotlin
@TimeLog
fun testTimeLog(){
    sleep(2000)
}
```



### FPS检测

```verilog
┌────────────────────────────────────────────────────────────────────────────────────────────────────────────
│ Thread: main
├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
│ XLog.kt   (33)   d$default
│ msg:APP FPS is 60.488003 HZ
└────────────────────────────────────────────────────────────────────────────────────────────────────────────
```



### 卡顿检测

```verilog
 ---------------------------Block msg--------------------------------
    java.lang.Thread.sleep(Native Method)
    java.lang.Thread.sleep(Thread.java:373)
    java.lang.Thread.sleep(Thread.java:314)
    com.zgy.xperformance.MainActivity.testTimeLog_aroundBody4(MainActivity.kt:31)
    com.zgy.xperformance.MainActivity$AjcClosure5.run(MainActivity.kt:1)
    org.aspectj.runtime.reflect.JoinPointImpl.proceed(JoinPointImpl.java:149)
    com.zgy.xperf.watch.TimeHooker.timeLog(TimeHooker.kt:23)
    com.zgy.xperformance.MainActivity.testTimeLog(MainActivity.kt:31)
    com.zgy.xperformance.MainActivity$onCreate$1.onClick_aroundBody0(MainActivity.kt:16)
    com.zgy.xperformance.MainActivity$onCreate$1$AjcClosure1.run(MainActivity.kt:1)
    org.aspectj.runtime.reflect.JoinPointImpl.proceed(JoinPointImpl.java:149)
    com.zgy.xperf.clickhooker.ClickHooker.clickMethod(ClickHooker.kt:36)
    com.zgy.xperformance.MainActivity$onCreate$1.onClick(MainActivity.kt:15)
    android.view.View.performClick(View.java:6897)
    android.widget.TextView.performClick(TextView.java:12693)
    android.view.View$PerformClick.run(View.java:26101)
    android.os.Handler.handleCallback(Handler.java:789)
    android.os.Handler.dispatchMessage(Handler.java:98)
    android.os.Looper.loop(Looper.java:164)
    android.app.ActivityThread.main(ActivityThread.java:6944)
    java.lang.reflect.Method.invoke(Native Method)
    com.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:327)
    com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1374)
    ---------------------------Block msg end--------------------------------
```

