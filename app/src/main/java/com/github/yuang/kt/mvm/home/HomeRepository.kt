package com.github.yuang.kt.mvm.home

import com.github.yuang.kt.android_mvvm.entity.BaseData
import com.github.yuang.kt.mvm.entity.SearchEntForSamplingBean
import rxhttp.toAwait
import rxhttp.wrapper.param.RxHttp

class HomeRepository {


    suspend fun searchEntForSampling(pageNumber: Int): BaseData<SearchEntForSamplingBean> {
        val mSearchEntForSamplingBean =
            RxHttp.get("http://******/ent/searchEntForSampling")
                .setHeader(
                    "token",
                    "app:2178a819ad3d4ab49c3bedc2a73c76d0:0200ca99534a42aaa909464b8f15e4c9"
                )
                .addQuery("pageNumber", pageNumber)
                .addQuery("pageSize", "10")
                .toAwait<SearchEntForSamplingBean>()
                .await()

        return BaseData("", mSearchEntForSamplingBean)
    }
}