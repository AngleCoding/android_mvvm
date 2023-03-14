package com.github.yuang.kt.android_mvvm.ext

import com.github.yuang.kt.android_mvvm.entity.BaseData
import com.github.yuang.kt.android_mvvm.exception.AppException


/**
 * 处理返回值
 *
 * @param result 请求结果
 */
fun <T> VmLiveData<T>.paresVmResult(result: BaseData<T>) {
    value = if (result.dataRight()) VmState.Success(result.result) else
        VmState.Error(AppException(result.getMsg()))
}

/**
 * 异常转换异常处理
 */
fun <T> VmLiveData<T>.paresVmException(e: Throwable) {
    this.value = VmState.Error(AppException(e))
}