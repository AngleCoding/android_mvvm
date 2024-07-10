package com.github.yuang.kt.mvm.main

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.github.yuang.kt.android_mvvm.BaseApp
import com.github.yuang.kt.mvm.login.LoginActivity


/**
 * @author AnglePenCoding
 * Created by on 2023/2/15 0015
 * @website https://github.com/AnglePengCoding
 */
class MyApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}