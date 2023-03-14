package com.github.yuang.kt.android_mvvm.ext

import com.github.yuang.kt.android_mvvm.exception.AppException

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