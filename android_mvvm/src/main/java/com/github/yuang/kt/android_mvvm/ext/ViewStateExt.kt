package com.github.yuang.kt.android_mvvm.ext

import com.github.yuang.kt.android_mvvm.entity.BaseData
import com.github.yuang.kt.android_mvvm.exception.AppException
import com.github.yuang.kt.android_mvvm.exception.TokenFailureException

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */


/**
 * 处理返回值
 *
 * @param result 请求结果
 */
fun <T> VmLiveData<T>.paresVmResult(result: BaseData<T>) {
    value = when (result.code) {
        "200" -> VmState.Success(result.result)
        "401" -> VmState.TokenFailure(result.getMsg())
        else -> VmState.Error(AppException(result.getMsg()))
    }
}

/**
 * 异常转换异常处理
 */
fun <T> VmLiveData<T>.paresVmException(e: Throwable) {
    this.value = VmState.Error(AppException(e))
}