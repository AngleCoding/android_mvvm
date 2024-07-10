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
        val map = mapOf("account" to "gzqnld", "password" to "Aa12345", "type" to "app")
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            map.toJsonString()
        )
        val loginBean = RxHttp.postBody("http://zyc.creditmoa.cn/" + "login")
            .setBody(body)
            .toAwait<LoginBean>()
            .await()
        return BaseData("token失效", loginBean, "401")
    }

}