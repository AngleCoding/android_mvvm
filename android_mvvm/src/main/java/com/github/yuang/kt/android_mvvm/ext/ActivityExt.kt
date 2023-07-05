package com.github.yuang.kt.android_mvvm.ext

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */

fun AppCompatActivity.initImmersionBar(
    activity: AppCompatActivity,
    statusBarView: View,
    isDarkFont: Boolean
) {
    ImmersionBar.with(activity).apply {
        transparentBar()
        statusBarDarkFont(isDarkFont)
        statusBarView(statusBarView)
        hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
        init()
    }
}


/**
 * 软键盘的隐藏
 * [view] 事件控件View
 */
fun Activity.dismissKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}


/**
 * Activity 跳转
 */
inline fun <reified T : AppCompatActivity> Context?.startActivity() =
    this?.startActivity(Intent(this, T::class.java))


/**
 * 携带数据
 */

inline fun <reified T : AppCompatActivity> Context?.startActivity(bundle: Bundle) =
    this?.startActivity(Intent(this, T::class.java).putExtras(bundle))


/**
 * 扩展lifeCycleOwner属性，便于和Fragment之间使用lifeCycleOwner 一致性
 */
val AppCompatActivity.viewLifeCycleOwner: LifecycleOwner
    get() = this


/**
 * Activity的扩展字段，便于和Fragment中使用liveData之类的时候，参数一致性
 */
val AppCompatActivity.context: Context
    get() = this


/**
 * 启动service
 */

inline fun <reified T : Service> Context?.startSerview() =
    this?.startService(Intent(this, T::class.java))




