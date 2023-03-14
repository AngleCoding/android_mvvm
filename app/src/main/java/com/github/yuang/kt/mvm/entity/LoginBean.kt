package com.bj.naxx.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.github.yuang.kt.android_mvvm.ext.toJsonString
import kotlinx.android.parcel.Parcelize

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */
@Keep
@Parcelize
data class LoginBean( val `result`: Result,
                      val code: Int,
                      val msg: String) : Parcelable{
    override fun toString(): String {
        return toJsonString()
    }
}

@Keep
@Parcelize
data class Result(
    val versionCode: String,
    val category: String?
) : Parcelable {
    override fun toString(): String {
        return this.toJsonString()
    }
}

