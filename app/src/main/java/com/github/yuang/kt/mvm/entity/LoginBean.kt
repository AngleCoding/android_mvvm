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
 class LoginBean(
    val msg: String,
    val result: Result,
    val ext: String,
    val code: String
)

class Result(
    val user: User,
    val token: String
)

class User(
    val account: String,
    val name: String,
    val password: Any,
    val salt: Any,
    val tel: Any,
    val category: String,
    val orgId: String,
    val orgName: String,
    val areaCode: String,
    val areaName: String,
    val level: Int,
    val duty: Any,
    val post: Any,
    val department: String,
    val executiveLevel: Any,
    val headPortrait: Any,
    val birthday: Any,
    val email: Any,
    val idCard: Any,
    val sex: Any,
    val officePhone: Any,
    val autograph: Any,
    val sort: Int,
    val platTitle: Any,
    val roles: Any,
    val menus: Any,
    val geoinfo: Any,
    val industry: Any,
    val org: Any,
    val pager: Any,
    val createdBy: String,
    val createdTime: String,
    val updatedBy: Any,
    val updatedTime: String,
    val status: String,
    val id: String
)