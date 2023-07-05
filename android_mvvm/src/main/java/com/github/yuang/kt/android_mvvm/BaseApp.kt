package com.github.yuang.kt.android_mvvm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.blankj.utilcode.util.LogUtils
import com.github.yuang.kt.android_mvvm.utils.AmapLocationUtils
import com.readystatesoftware.chuck.ChuckInterceptor
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
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

        @SuppressLint("StaticFieldLeak")
        lateinit var instance: BaseApp

        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context

        var mLocation = AMapLocation("")
        fun getLocation(): AMapLocation {
            return mLocation
        }
    }

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { _, layout ->
            layout.apply {
                setEnableLoadMore(false)
                autoRefresh()
                finishRefresh(2000)
                setPrimaryColorsId(R.color.font_blue, android.R.color.white)
            }
            ClassicsHeader(mContext)
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = this
        initAutoSize()
        initLocation()
        initRxHttp()
    }

    private fun initLocation() {
        AMapLocationClient.updatePrivacyShow(this, true, true)
        AMapLocationClient.updatePrivacyAgree(this, true)

        AmapLocationUtils.getInstance().initLocation(mContext, object : AmapLocationUtils.Callback {
            override fun onSuccess(location: AMapLocation) {
                mLocation = location
            }

            override fun onFailure(message: String?) {
                LogUtils.e("定位失败： $message")
            }

        })
        AmapLocationUtils.getInstance().stopLocation()

    }

    private fun initRxHttp() {
        val builder = OkHttpClient.Builder()
        val chuckInterceptor = ChuckInterceptor(mContext)
        chuckInterceptor.showNotification(false)
        builder.addInterceptor(chuckInterceptor)

        init(builder.build()).setDebug(true)
    }

    private fun initAutoSize() {
        AutoSize.initCompatMultiProcess(mContext)
        AutoSizeConfig.getInstance().apply {
            isBaseOnWidth = true
            isCustomFragment = true
        }
    }


}