package com.github.yuang.kt.mvm.home

import androidx.lifecycle.MutableLiveData
import com.github.yuang.kt.android_mvvm.base.BaseViewModel
import com.github.yuang.kt.android_mvvm.ext.VmLiveData
import com.github.yuang.kt.android_mvvm.ext.launchVmRequest
import com.github.yuang.kt.mvm.entity.SearchEntForSamplingBean

class HomeViewModel : BaseViewModel() {

    private val mHomeRepository by lazy { HomeRepository() }

    val refreshData: VmLiveData<SearchEntForSamplingBean> = MutableLiveData()

    var pageNumber: Int = 1


    fun searchEntForSamplingF() {
        pageNumber = 1
        searchEntForSampling(this.pageNumber)
    }

    fun searchEntForSamplingMore() {
        this.pageNumber++
        searchEntForSampling(this.pageNumber)
    }

    private fun searchEntForSampling(pageNumber: Int) {
        launchVmRequest({
            mHomeRepository.searchEntForSampling(pageNumber)
        }, refreshData)
    }
}