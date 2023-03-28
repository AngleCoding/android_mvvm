package com.github.yuang.kt.mvm.main

import com.github.yuang.kt.android_mvvm.base.BaseApp


/**
 * @author AnglePenCoding
 * Created by on 2023/2/15 0015
 * @website https://github.com/AnglePengCoding
 */
class MyApp : BaseApp() {
    companion object {
        private var instance: MyApp? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


    }

    override fun baseUrl(): String {
        return "http://120.221.72.37:8095/"
    }
}