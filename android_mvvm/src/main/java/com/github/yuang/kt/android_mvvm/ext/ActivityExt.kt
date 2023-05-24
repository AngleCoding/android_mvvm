package com.github.yuang.kt.android_mvvm.ext

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
fun AppCompatActivity.dismissKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}


/**
 * 跳转页面
 *
 */
fun AppCompatActivity.startActvity(packageContext: Context, cls: Class<Any>) {
    val intent = Intent(packageContext, cls::class.java)
    startActivity(intent)
}

/**
 * 跳转页面
 *携带数据
 */
fun AppCompatActivity.startActvity(packageContext: Context, cls: Class<Any>, bundle: Bundle) {
    val intent = Intent(packageContext, cls::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}



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
