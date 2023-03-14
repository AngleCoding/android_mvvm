package com.github.yuang.kt.android_mvvm.entity

class BaseData <T>(
    private var msg: String = "查询成功",
    var result: T,
    var code: String = "200"
) {

    /**
     * 数据是否正确，默认实现
     */
    open fun dataRight(): Boolean {
        return code == "200"
    }

    /**
     * 获取错误信息，默认实现
     */
    open fun getMsg(): String {
        return msg
    }
}