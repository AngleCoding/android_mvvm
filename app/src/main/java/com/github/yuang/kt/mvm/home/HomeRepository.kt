package com.github.yuang.kt.mvm.home

import com.github.yuang.kt.android_mvvm.entity.BaseData
import com.github.yuang.kt.mvm.entity.SearchEntForSamplingBean
import rxhttp.toAwait
import rxhttp.wrapper.param.RxHttp

class HomeRepository {


     suspend fun searchEntForSampling(): BaseData<SearchEntForSamplingBean> {
//        http://120.221.72.37:8095/ent/searchEntForSampling?pageNumber=1&name=&pageSize=10
        val mSearchEntForSamplingBean =
            RxHttp.get("http://120.221.72.37:8095/ent/searchEntForSampling")
                .setHeader(
                    "token",
                    "app:2178a819ad3d4ab49c3bedc2a73c76d0:7bc8ebb2e9bf409b9dc1a73ca70c6a89"
                )
                .toAwait<SearchEntForSamplingBean>()
                .await()

        return BaseData("", mSearchEntForSamplingBean)
    }
}