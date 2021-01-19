package com.zgy.xperf.activityhooker


class ActivityRecord {
    /**
     * 避免没有仅执行onResume就去统计界面打开速度的情况，如息屏、亮屏等等
     */
    var isNewCreate = false

    var mOnCreateTime: Long = 0
    var mOnWindowsFocusChangedTime: Long = 0
}
