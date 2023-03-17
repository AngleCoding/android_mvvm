package com.github.yuang.kt.android_mvvm.base

import android.app.Application
import android.content.Context
import com.github.yuang.kt.android_mvvm.R
import com.readystatesoftware.chuck.ChuckInterceptor
import com.readystatesoftware.chuck.internal.support.NotificationHelper
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator
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

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { _, layout ->
            layout.apply {
                setEnableLoadMore(false)
                autoRefresh()
                finishRefresh(2000)
                setPrimaryColorsId(R.color.font_blue, android.R.color.white)
            }
            ClassicsHeader(applicationContext)
        }
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