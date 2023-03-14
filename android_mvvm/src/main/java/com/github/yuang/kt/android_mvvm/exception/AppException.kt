package com.github.yuang.kt.android_mvvm.exception

import com.github.yuang.kt.android_mvvm.ext.parseErrorString

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */
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