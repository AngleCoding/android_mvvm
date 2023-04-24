package com.github.yuang.kt.android_mvvm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.readystatesoftware.chuck.ChuckInterceptor
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.tencent.tmf.hotpatch.api.application.HotpatchApplication
import com.tencent.tmf.hotpatchcore.HotPatch
import com.tencent.tmf.hotpatchcore.Protocol.ProtocolType.PROTOCOL_TYPE_SHARK
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins.init

/**
 * 热修复
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */
open class BaseHotPatchApp : HotpatchApplication(
    Application(),
    ShareConstants.TINKER_ENABLE_ALL,
    false,
    SystemClock.elapsedRealtime(),
    System.currentTimeMillis(),
    Intent()
) {


    override fun onCreate() {
        super.onCreate()
        initHotPatch()
    }

    /**
     * 热修复初始化
     * @see 具体请移步 https://cloud.tencent.com/document/product/1034/86567#sdk-.E9.9B.86.E6.88.90
     *  新建app https://console.cloud.tencent.com/tmf/hotp/hotfix
     */
    private fun initHotPatch() {
        HotPatch.init(application.applicationContext, PROTOCOL_TYPE_SHARK)
    }

}