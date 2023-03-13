package com.github.yuang.kt.ext

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ScreenUtils
import com.gyf.immersionbar.ImmersionBar


fun AppCompatActivity.initImmersionBar(activity: AppCompatActivity, statusBarView: View) {
    ScreenUtils.setPortrait(activity)
    ImmersionBar.with(activity).apply {
        transparentBar()
        statusBarDarkFont(true)
        statusBarView(statusBarView)
        navigationBarEnable(true)
        keyboardEnable(true)
        init()
    }
}