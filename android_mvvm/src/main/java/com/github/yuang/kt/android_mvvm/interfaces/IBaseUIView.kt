package com.github.yuang.kt.android_mvvm.interfaces

import com.github.yuang.kt.android_mvvm.enmus.BaseViewStatus

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */

interface IBaseUIView {

    /** 获取当前布局状态,方便在callBack中统一处理  */
    fun getBaseViewStatus(): BaseViewStatus?

    /** 手动设置布局状态,一般情况不需要  */
    fun setBaseViewStatus(baseViewStatus: BaseViewStatus?)

    /** 显示成功布局  */
    fun showSuccessLayout()

    /** 显示加载中布局  */
    fun showLoadingLayout()

    /** 显示失败布局  */
    fun showErrorLayout(errorMsg: String?)

    /**
     * 网络请求加载中间弹窗
     */
    fun showLoadingDialog()

    fun dismissLoadingDialog(baseViewStatus: BaseViewStatus)
}