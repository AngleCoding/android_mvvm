package com.github.yuang.kt.android_mvvm.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.android_mvvm.base.BaseFragment
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */

/**
 * 设置onclick事件
 */
fun setViewsOnClickListener(listener: View.OnClickListener, vararg views: View) {
    views.forEach { it.setOnClickListener(listener) }
}


/**
 * context显示toast
 */
fun Context.showToast(tips: String) {
    ToastUtils.showShort(tips)
}



/**
 * Fragment显示toast
 */
fun Fragment.showToast(tips: String) {
    ToastUtils.showShort(tips)
}

/**
 * 弹起键盘
 */
fun Activity.showSoftKeyboard(editText: EditText) {
    editText.postDelayed({
        editText.requestFocus()
        val inputMethodMananger =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodMananger.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }, 30)
}


/**
 * 隐藏键盘
 */
fun Context.hideSoftInput() {
    val imm: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm?.hideSoftInputFromWindow((this as Activity).window.decorView.windowToken, 0)
}

/**
 * 快捷创建viewmodel
 */
inline fun <reified T : ViewModel> BaseActivity.getViewModel(): T =
    ViewModelProvider(this).get(T::class.java)

/**
 * 快捷创建viewmodel
 */
inline fun <reified T : ViewModel> BaseFragment.getViewModel(): T =
    ViewModelProvider(this).get(T::class.java)


/**
 * 获取颜色
 */
fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)


/**
 * 获取布局
 */
val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.inflateLayout(
    @LayoutRes layoutId: Int,
    parent: ViewGroup? = null,
    attachToRoot: Boolean = false
) = inflater.inflate(layoutId, parent, attachToRoot)


/**
 * 获取资源
 */
fun Context.getCompatColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.getCompatDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)


/**
 * 获取系统Manage
 */

val Context.inputManager: InputMethodManager get() = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager


val Context.notificationManager: NotificationManager get() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


/**
 * 跳转拨号界面
 */
fun Context.makeCall(number: String): Boolean {
    try {
        val intent = Intent(ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
        return true
    } catch (e: Exception) {
        return false
    }
}


/**
 * 获取子view集合
 */
val ViewGroup.children: List<View> get() = (0 until childCount).map { getChildAt(it) }


/**
 * 按钮监听
 */
@SuppressLint("CheckResult")
fun <T : View> T.click(block: () -> Unit) =
    clicks()
        .throttleFirst(3000, TimeUnit.MILLISECONDS)
        .subscribe {
            block()
        }



