package com.github.yuang.kt.android_mvvm.ext

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.github.yuan.picture_take.utils.ToastUtils.showToast
import com.github.yuang.kt.android_mvvm.entity.BaseData
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.android_mvvm.base.BaseFragment
import com.github.yuang.kt.android_mvvm.base.BaseViewModel
import com.github.yuang.kt.android_mvvm.enmus.BaseViewStatus
import com.github.yuang.kt.android_mvvm.exception.AppException
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    activity: BaseActivity,
    lottieLoadingUrl: String = "lottie/loading-animation-blue.json",
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(activity) {
        if (it is VmState.Loading) {
            activity.setBaseViewStatus(BaseViewStatus.LOADING, lottieLoadingUrl)
        } else if (it is VmState.Success) {
            onSuccess(it.result)
            activity.setBaseViewStatus(BaseViewStatus.SUCCESS, lottieLoadingUrl)
        } else if (it is VmState.Error) {
            activity.showToast(it.error.errorMsg)
            activity.showErrorLayout(it.error.errorMsg)
        } else if (it is VmState.TokenFailure) {
            activity.logOut()
        } else if (it is VmState.FailToast) {
            activity.setBaseViewStatus(BaseViewStatus.SUCCESS, lottieLoadingUrl)
            activity.showToast(it.msg)
        }
    }
}


/**
 * 默认不带loading的网络请求
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    activity: BaseActivity,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(activity) {
        if (it is VmState.Loading) {
        } else if (it is VmState.Success) {
            activity.setBaseViewStatus(BaseViewStatus.SUCCESS, null)
            onSuccess(it.result)
        } else if (it is VmState.Error) {
            activity.showToast(it.error.errorMsg)
            activity.showErrorLayout(it.error.errorMsg)
        } else if (it is VmState.TokenFailure) {
            activity.logOut()
        } else if (it is VmState.FailToast) {
            activity.setBaseViewStatus(BaseViewStatus.SUCCESS, null)
            activity.showToast(it.msg)
        }
    }
}

/**
 * 带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverLoading(
    activity: BaseActivity,
    loadTxt: String? = "加载中...",
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(activity) {
        when (it) {
            is VmState.Loading -> {
                activity.showLoadingDialog(loadTxt)
            }

            is VmState.Success -> {
                onSuccess(it.result)
                activity.dismissLoadingDialog(BaseViewStatus.SUCCESS)
            }

            is VmState.Error -> {
                activity.showToast(it.error.errorMsg)
                activity.dismissLoadingDialog(BaseViewStatus.ERROR)
            }

            is VmState.TokenFailure -> {
                activity.logOut()
            }

            is VmState.FailToast -> {
                activity.dismissLoadingDialog(BaseViewStatus.SUCCESS)
                activity.showToast(it.msg)
            }
        }
    }
}


@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    fragment: BaseFragment,
    lottieLoadingUrl: String = "lottie/loading-animation-blue.json",
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        if (it is VmState.Loading) {
            fragment.setBaseViewStatus(BaseViewStatus.LOADING, lottieLoadingUrl)
        } else if (it is VmState.Success) {
            onSuccess(it.result)
            fragment.setBaseViewStatus(BaseViewStatus.SUCCESS, lottieLoadingUrl)
        } else if (it is VmState.Error) {
            fragment.requireContext().showToast(it.error.errorMsg)
            fragment.showErrorLayout(it.error.errorMsg)
        } else if (it is VmState.TokenFailure) {
            fragment.logOut()
        } else if (it is VmState.FailToast) {
            fragment.setBaseViewStatus(BaseViewStatus.SUCCESS, lottieLoadingUrl)
            fragment.requireContext().showToast(it.msg)
        }
    }
}


/**
 * 默认不带loading的网络请求
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    fragment: BaseFragment,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        if (it is VmState.Loading) {
        } else if (it is VmState.Success) {
            fragment.setBaseViewStatus(BaseViewStatus.SUCCESS, null)
            onSuccess(it.result)
        } else if (it is VmState.Error) {
            if (null != tips && tips) fragment.requireContext().showToast(it.error.errorMsg)
            fragment.showErrorLayout(it.error.errorMsg)
        } else if (it is VmState.TokenFailure) {
            fragment.logOut()
        } else if (it is VmState.FailToast) {
            fragment.setBaseViewStatus(BaseViewStatus.SUCCESS, null)
            fragment.requireContext().showToast(it.msg)
        }
    }
}

/**
 * 带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverLoading(
    fragment: BaseFragment,
    tips: Boolean? = true,
    loadTxt: String? = "加载中...",
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
                fragment.showLoadingDialog(loadTxt)
            }

            is VmState.Success -> {
                onSuccess(it.result)
                fragment.dismissLoadingDialog(BaseViewStatus.SUCCESS)
            }

            is VmState.Error -> {
                if (null != tips && tips) fragment.requireContext().showToast(it.error.errorMsg)
                fragment.dismissLoadingDialog(BaseViewStatus.ERROR)
            }

            is VmState.TokenFailure -> {
                fragment.logOut()
            }

            is VmState.FailToast -> {
                fragment.dismissLoadingDialog(BaseViewStatus.SUCCESS)
                fragment.requireContext().showToast(it.msg)
            }
        }
    }
}


/**
 * BaseViewModel开启协程扩展
 */
fun <T> BaseViewModel.launchVmRequest(
    request: suspend () -> BaseData<T>,
    viewState: VmLiveData<T>
) {
    viewModelScope.launch {
        runCatching {
            viewState.value = VmState.Loading
            request()

        }.onSuccess {
            viewState.paresVmResult(it)
        }.onFailure {
            viewState.paresVmException(it)
        }
    }
}


/**
 * 网络错误提示
 */
fun Throwable?.parseErrorString(): String {
    return when (this) {
        is SocketException -> "网络错误"
        is ConnectException -> "网络错误"
        is UnknownHostException -> "无网络连接"
        is JsonSyntaxException -> "数据错误,json错误"
        is SocketTimeoutException -> "网络错误"
        is TimeoutException -> "网络超时"
        else -> "未知错误"
    }
}