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
import androidx.lifecycle.lifecycleScope
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

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
        fitsSystemWindows(true)
        keyboardEnable(true)
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



/**
 * 短信倒计时
 *
 *         countDown(start = {
 *             binding.mBtLO.isEnabled = false
 *         }, end = {
 *             binding.mBtLO.isEnabled = true
 *         }, next = { time ->
 *             binding.mBtLO.text = time.toString()
 *         }, error = { msg ->
 *             binding.mBtLO.text = msg
 *         })
 *
 */
fun AppCompatActivity.countDown(
    timeMillis: Long = 1000,//默认时间间隔 1 秒
    time: Int = 60,//默认时间为 3 秒
    start: (scop: CoroutineScope) -> Unit,
    end: () -> Unit,
    next: (time: Int) -> Unit,
    error: (msg: String?) -> Unit
) {
    lifecycleScope.launch {
        flow {
            (time downTo 0).forEach {
                delay(timeMillis)
                emit(it)
            }
        }.onStart {
            start(this@launch)
        }.onCompletion {
            end()
        }.catch {
            error(it.message ?: "出现未知错误")
        }.collect {
            next(it)
        }
    }
}


