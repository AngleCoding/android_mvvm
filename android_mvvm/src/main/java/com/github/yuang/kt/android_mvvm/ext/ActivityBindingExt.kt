package com.github.yuang.kt.android_mvvm.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ScreenUtils
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
 * 扩展lifeCycleOwner属性，便于和Fragment之间使用lifeCycleOwner 一致性
 */
val AppCompatActivity.viewLifeCycleOwner: LifecycleOwner
    get() = this


/**
 * Activity的扩展字段，便于和Fragment中使用liveData之类的时候，参数一致性
 */
val AppCompatActivity.context: Context
    get() = this
