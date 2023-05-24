package com.github.yuang.kt.android_mvvm.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.github.yuang.kt.android_mvvm.BaseApp


/**
 * 跳转页面
 *
 */
fun Fragment.startActvity(packageContext: Context, cls: Class<Any>) {
    val intent = Intent(packageContext, cls::class.java)
    startActivity(intent)
}


/**
 * 软键盘的隐藏
 * [view] 事件控件View
 */
fun Fragment.dismissKeyBoard(view: View) {
    val imm = BaseApp.mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * 跳转页面
 *携带数据
 */
fun Fragment.startActvity(packageContext: Context, cls: Class<Any>, bundle: Bundle) {
    val intent = Intent(packageContext, cls::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}



