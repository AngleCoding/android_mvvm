package com.github.yuang.kt.android_mvvm.ext

import android.util.Log
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
    if (result.code == "00000") {
        value = VmState.Success(result.data)
        Log.e("VmLiveData", "Success")
    } else if (result.code == "A00008") {
        value = VmState.TokenFailure(result.getMsg())
        Log.e("VmLiveData", "TokenFailure")
    }else if (result.code == "A00004") {
        value = VmState.TokenFailure(result.getMsg())
        Log.e("VmLiveData", "TokenFailure")
    }else if (result.code == "A00009") {
        value = VmState.TokenFailure(result.getMsg())
        Log.e("VmLiveData", "TokenFailure")
    }
    else {
        value = VmState.FailToast(result.getMsg())
        Log.e("VmLiveData", "else")
    }

}

/**
 * 异常转换异常处理
 */
fun <T> VmLiveData<T>.paresVmException(e: Throwable) {
    this.value = VmState.Error(AppException(e))
}