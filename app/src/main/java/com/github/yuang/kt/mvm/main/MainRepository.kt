package com.github.yuang.kt.mvm.main

import com.bj.naxx.entity.LoginBean
import com.github.yuang.kt.android_mvvm.entity.BaseData
import rxhttp.toAwait
import rxhttp.wrapper.param.RxHttp

class MainRepository {

    suspend fun login(): BaseData<LoginBean> {
        val loginBean = RxHttp.get("http://120.221.72.37:8095/" + "version/getLatestOne")
            .toAwait<LoginBean>()
            .await()
        return BaseData("", loginBean)
    }

}