package com.github.yuang.kt.android_mvvm.base

import android.app.Application
import com.readystatesoftware.chuck.ChuckInterceptor
import com.readystatesoftware.chuck.internal.support.NotificationHelper
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins.init

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */
abstract class BaseApp : Application() {

    companion object {
        private var instance: BaseApp? = null
        fun instance() = instance!!
    }

    abstract fun baseUrl(): String

    override fun onCreate() {
        super.onCreate()
        instance = this

        initAutoSize()
        initRxHttp()
    }

    private fun initRxHttp() {
        val builder = OkHttpClient.Builder()
        val chuckInterceptor = ChuckInterceptor(applicationContext)
        chuckInterceptor.showNotification(false)
        builder.addInterceptor(chuckInterceptor)

        init(builder.build()).setDebug(true)
    }

    private fun initAutoSize() {
        AutoSize.initCompatMultiProcess(this)
        AutoSizeConfig.getInstance().apply {
            isBaseOnWidth = true
            isCustomFragment = true
        }
    }


}