package com.github.yuang.kt.android_mvvm.exception

import com.github.yuang.kt.android_mvvm.ext.parseErrorString

class AppException : Exception {
    var errorMsg: String

    constructor(error: String?) : super() {
        errorMsg = error ?: parseError(null)
    }

    constructor(throwable: Throwable?) : super() {
        errorMsg = parseError(throwable)
    }

    private fun parseError(throwable: Throwable?): String {
        return throwable.parseErrorString()
    }

}