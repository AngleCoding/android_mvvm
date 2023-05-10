package com.github.yuang.kt.mvm.main

import com.bj.naxx.entity.LoginBean
import com.github.yuang.kt.android_mvvm.entity.BaseData
import com.github.yuang.kt.android_mvvm.ext.toJsonString
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import rxhttp.toAwait
import rxhttp.wrapper.param.RxHttp

class MainRepository {

    suspend fun login(): BaseData<LoginBean> {
        val map = mapOf("account" to "111", "password" to "22", "type" to "app")
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            map.toJsonString()
        )
        val loginBean = RxHttp.postBody("https://publicres.creditmoa.com/login")
            .setBody(body)
            .toAwait<LoginBean>()
            .await()
        return BaseData("", loginBean,"","")
    }

}