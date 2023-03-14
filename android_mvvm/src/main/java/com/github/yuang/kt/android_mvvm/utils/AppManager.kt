package com.github.yuang.kt.android_mvvm.utils

import android.app.Activity
import java.util.*

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */

object AppManager {


    /**
     * 维护Activity 的list
     */
    var mActivitys = Collections.synchronizedList(LinkedList<Activity>())

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    fun pushActivity(activity: Activity?) {
        mActivitys.add(activity)
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    fun popActivity(activity: Activity?) {
        mActivitys.remove(activity)
    }


    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return if (mActivitys.isEmpty()) {
            null
        } else mActivitys[mActivitys.size - 1]
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    fun finishCurrentActivity() {
        if (mActivitys.isEmpty()) {
            return
        }
        val activity = mActivitys[mActivitys.size - 1]
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (mActivitys.isEmpty()) {
            return
        }
        if (activity != null) {
            mActivitys.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        if (mActivitys.isEmpty()) {
            return
        }
        for (activity in mActivitys) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    fun findActivity(cls: Class<*>): Activity? {
        var targetActivity: Activity? = null
        for (activity in mActivitys) {
            if (activity.javaClass == cls) {
                targetActivity = activity
                break
            }
        }
        return targetActivity
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    fun getTopActivity(): Activity? {
        var mBaseActivity: Activity? = null
        synchronized(mActivitys) {
            val size = mActivitys.size - 1
            if (size < 0) {
                return null
            }
            mBaseActivity = mActivitys[size]
        }
        return mBaseActivity
    }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    fun getTopActivityName(): String? {
        var mBaseActivity: Activity? = null
        synchronized(mActivitys) {
            val size = mActivitys.size - 1
            if (size < 0) {
                return null
            }
            mBaseActivity = mActivitys[size]
        }
        return mBaseActivity!!.javaClass.name
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activity in mActivitys) {
            activity.finish()
        }
        mActivitys.clear()
    }

    /**
     * 退出应用程序
     */
    fun appExit() {
        try {
            finishAllActivity()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}