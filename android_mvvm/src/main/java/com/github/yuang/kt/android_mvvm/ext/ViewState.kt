package com.github.yuang.kt.android_mvvm.ext

import com.github.yuang.kt.android_mvvm.exception.AppException
import rxhttp.wrapper.exception.ParseException

//import com.github.yuang.kt.android_mvvm.exception.AppException

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */

class VmResult<T> {
    var onAppSuccess: (data: T) -> Unit = {}
    var onAppError: (exception: AppException) -> Unit = {}
    var onAppLoading: () -> Unit = {}
    var onAppComplete: () -> Unit = {}
}

sealed class VmState<out T> {
    object Loading : VmState<Nothing>()
    data class Success<out T>(val result: T) : VmState<T>()
    data class Error(val error: AppException) : VmState<Nothing>()
}