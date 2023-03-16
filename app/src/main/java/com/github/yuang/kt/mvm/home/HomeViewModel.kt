package com.github.yuang.kt.mvm.home

import androidx.lifecycle.MutableLiveData
import com.github.yuang.kt.android_mvvm.base.BaseViewModel
import com.github.yuang.kt.android_mvvm.ext.VmLiveData
import com.github.yuang.kt.android_mvvm.ext.launchVmRequest
import com.github.yuang.kt.mvm.entity.SearchEntForSamplingBean

class HomeViewModel : BaseViewModel() {

    private val mHomeRepository by lazy { HomeRepository() }

    private val refreshData: VmLiveData<SearchEntForSamplingBean> = MutableLiveData()

    fun searchEntForSampling() {
        launchVmRequest({
            mHomeRepository.searchEntForSampling()
        }, refreshData)
    }

}