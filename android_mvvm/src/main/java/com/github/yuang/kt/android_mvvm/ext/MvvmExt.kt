package com.github.yuang.kt.android_mvvm.ext

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.github.yuang.kt.android_mvvm.entity.BaseData
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.android_mvvm.base.BaseFragment
import com.github.yuang.kt.android_mvvm.base.BaseViewModel
import com.github.yuang.kt.android_mvvm.enmus.BaseViewStatus
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */


/**
 * 跟下面的方法类似,只是调用形式上有所区别
 * 这种vmResult要提前定义好
 * 下面vmResult: VmResult<T>.() -> Unit可以直接写在参数里面
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserver(owner: LifecycleOwner, vmResult: VmResult<T>) {
    observe(owner) {
        if (it is VmState.Loading) {
            vmResult.onAppLoading()
        } else if (it is VmState.Success) {
            vmResult.onAppSuccess(it.result)
            vmResult.onAppComplete()
        } else if (it is VmState.Error) {
            vmResult.onAppError(it.error)
            vmResult.onAppComplete()
        }
    }
}


/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    activity: BaseActivity,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(activity) {
        if (it is VmState.Loading) {
            activity.setBaseViewStatus(BaseViewStatus.LOADING)
            activity.showLoadingLayout()
        } else if (it is VmState.Success) {
            activity.setBaseViewStatus(BaseViewStatus.SUCCESS)
            onSuccess(it.result)
            activity.showSuccessLayout()
        } else if (it is VmState.Error) {
            activity.showErrorLayout(it.error.errorMsg)
        }
    }
}

/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    activity: BaseActivity,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(activity) {
        if (it is VmState.Loading) {
            if (activity.getBaseViewStatus() != BaseViewStatus.SUCCESS) activity.showLoadingLayout()
        } else if (it is VmState.Success) {
            onSuccess(it.result)
            activity.showSuccessLayout()
            onComplete()
        } else if (it is VmState.Error) {
            if (null != tips && tips) activity.showToast(it.error.errorMsg)
            if (activity.getBaseViewStatus() != BaseViewStatus.SUCCESS) {
                activity.showErrorLayout(it.error.errorMsg)
                onComplete()
            }
        }
    }
}

/**
 * 重写所有回调方法
 * onAppLoading
 * onAppSuccess
 * onAppError
 * onAppComplete
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserver(owner: LifecycleOwner, vmResult: VmResult<T>.() -> Unit) {
    val result = VmResult<T>();result.vmResult();observe(owner) {
        if (it is VmState.Loading) {
            result.onAppLoading()
        } else if (it is VmState.Success) {
            result.onAppSuccess(it.result);result.onAppComplete()
        } else if (it is VmState.Error) {
            result.onAppError(it.error);result.onAppComplete()
        }
    }
}

/**
 * 带loading的网络请求
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverLoading(
    activity: BaseActivity,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(activity) {
        if (it is VmState.Loading) {
            activity.setBaseViewStatus(BaseViewStatus.LOADING)
            activity.showLoadingDialog()
        } else if (it is VmState.Success) {
            activity.setBaseViewStatus(BaseViewStatus.SUCCESS)
            onSuccess(it.result)
            activity.dismissLoadingDialog()
        } else if (it is VmState.Error) {
            activity.setBaseViewStatus(BaseViewStatus.ERROR)
            if (null != tips && tips) activity.showToast(it.error.errorMsg)
            activity.dismissLoadingDialog()
        }
    }
}

/**
 * 带loading的网络请求
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverLoading(
    activity: BaseActivity,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(activity) {
        if (it is VmState.Loading) {
            activity.showLoadingDialog()
        } else if (it is VmState.Success) {
            onSuccess(it.result)
            activity.dismissLoadingDialog()
            onComplete()
        } else if (it is VmState.Error) {
            if (null != tips && tips) activity!!.showToast(it.error.errorMsg)
            activity.dismissLoadingDialog()
            onComplete()
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
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(activity) {
        if (it is VmState.Loading) {
        } else if (it is VmState.Success) {
            onSuccess(it.result)
        } else if (it is VmState.Error) {
            if (null != tips && tips) activity.showToast(it.error.errorMsg)
        }
    }
}

/**
 * 默认不带loading的网络请求
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    activity: BaseActivity,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(activity) {
        if (it is VmState.Loading) {
        } else if (it is VmState.Success) {
            onSuccess(it.result)
            onComplete()
        } else if (it is VmState.Error) {
            if (null != tips && tips) activity.showToast(it.error.errorMsg)
            onComplete()
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
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading ->{
                fragment.showLoadingDialog()
            }
            is VmState.Success -> {
                onSuccess(it.result)
                fragment.dismissLoadingDialog()
            }
            is VmState.Error -> {
                if(null != tips && tips) fragment.requireContext().showToast(it.error.errorMsg)
                fragment.dismissLoadingDialog()
            }
        }
    }
}

/**
 * 带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverLoading(
    fragment: BaseFragment,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading ->{
                fragment.showLoadingDialog()
            }
            is VmState.Success -> {
                onSuccess(it.result)
                fragment.dismissLoadingDialog()
                onComplete()
            }
            is VmState.Error -> {
                if(null != tips && tips) fragment.requireContext().showToast(it.error.errorMsg)
                fragment.dismissLoadingDialog()
                onComplete()
            }
        }
    }
}

/**
 * 不带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    fragment: BaseFragment,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
            }
            is VmState.Success -> {
                onSuccess(it.result)
            }
            is VmState.Error -> {
                if (null != tips && tips) fragment.requireContext().showToast(it.error.errorMsg)
            }
        }
    }
}

/**
 * 不带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
//@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    fragment: BaseFragment,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
            }
            is VmState.Success -> {
                onSuccess(it.result)
                onComplete()
            }
            is VmState.Error -> {
                if (null != tips && tips) fragment.requireContext().showToast(it.error.errorMsg)
                onComplete()
            }
        }
    }
}

/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    fragment: BaseFragment,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
                fragment.setBaseViewStatus(BaseViewStatus.LOADING)
                fragment.showLoadingLayout()
            }
            is VmState.Success -> {
                fragment.setBaseViewStatus(BaseViewStatus.SUCCESS)
                fragment.showSuccessLayout()
                onSuccess(it.result)
            }
            is VmState.Error -> {
                if (null != tips && tips) fragment.requireContext().showToast(it.error.errorMsg)
                fragment.setBaseViewStatus(BaseViewStatus.ERROR)
                fragment.showErrorLayout(it.error.errorMsg)
            }
        }
    }
}

/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    fragment: BaseFragment,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
                fragment.setBaseViewStatus(BaseViewStatus.LOADING)
                fragment.showLoadingLayout()
            }
            is VmState.Success -> {
                onSuccess(it.result)
                fragment.setBaseViewStatus(BaseViewStatus.SUCCESS)
                fragment.showSuccessLayout()
                onComplete()
            }
            is VmState.Error -> {
                if (null != tips && tips) fragment.requireContext().showToast(it.error.errorMsg)
                if (fragment.getBaseViewStatus() != BaseViewStatus.SUCCESS) {
                    fragment.setBaseViewStatus(BaseViewStatus.ERROR)
                    fragment.showErrorLayout(it.error.errorMsg)
                }
                onComplete()
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
