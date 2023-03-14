package com.github.yuang.kt.mvm.main

import androidx.lifecycle.MutableLiveData
import com.bj.naxx.entity.LoginBean
import com.github.yuang.kt.android_mvvm.base.BaseViewModel
import com.github.yuang.kt.android_mvvm.ext.VmLiveData
import com.github.yuang.kt.android_mvvm.ext.launchVmRequest

/**
 * @author AnglePenCoding
 * Created by on 2023/2/16 0016
 * @website https://github.com/AnglePengCoding
 */
class MainViewModel : BaseViewModel() {

    private val mainRepository by lazy { MainRepository() }
    val refreshData: VmLiveData<LoginBean> = MutableLiveData()

    fun login() {
        launchVmRequest({
            mainRepository.login()
        }, refreshData)
    }
}