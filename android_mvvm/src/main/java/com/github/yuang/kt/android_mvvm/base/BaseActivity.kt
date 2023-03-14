package com.github.yuang.kt.android_mvvm.base

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.github.yuang.kt.android_mvvm.R
import com.github.yuang.kt.android_mvvm.databinding.ActivityBaseBinding
import com.github.yuang.kt.android_mvvm.databinding.BaseViewStubToolbarBinding
import com.github.yuang.kt.android_mvvm.enmus.BaseViewStatus
import com.github.yuang.kt.android_mvvm.ext.initImmersionBar
import com.github.yuang.kt.android_mvvm.interfaces.IBaseUIView
import com.github.yuang.kt.android_mvvm.utils.AppManager
import me.jessyan.autosize.internal.CustomAdapt

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */
abstract class BaseActivity : AppCompatActivity(), CustomAdapt, ViewModelProvider.Factory,
    IBaseUIView {

    lateinit var mContext: Context
    private lateinit var baseBinding: ActivityBaseBinding
    lateinit var baseToolbarBinding: BaseViewStubToolbarBinding//标题栏
    private var myBaseViewStatus = BaseViewStatus.SUCCESS
    private lateinit var loadingView: View
    private lateinit var errorView: View
    private var lottieErrorView: LottieAnimationView? = null
    private var lottieLoadingView: LottieAnimationView? = null

    abstract fun getBinding(): ViewBinding //绑定布局

    abstract fun initView(savedInstanceState: Bundle?) //初始化view

    abstract fun initData() //初始化数据
    abstract fun initViewModel()//数据请求
    open fun getBundleExtras(extras: Bundle?) {} //接收bundle数据


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        intent.extras?.let { getBundleExtras(it) }
        init(savedInstanceState)
        AppManager.pushActivity(this)
    }

    override fun onResume() {
        super.onResume()
        if (getBaseViewStatus() == BaseViewStatus.LOADING) {
            lottieLoadingView?.resumeAnimation()
        }
        if (getBaseViewStatus() == BaseViewStatus.ERROR) {
            lottieErrorView?.resumeAnimation()
        }
    }


    override fun onPause() {
        super.onPause()
        lottieLoadingView?.pauseAnimation()
        lottieErrorView?.pauseAnimation()
    }


    private fun init(savedInstanceState: Bundle?) {
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        initImmersionBar(this, baseBinding.statusBarView)
        initViewStubToolbarBinding()
        initViewStubLoading()
        initViewStubError()
        initMainBinding()
        initView(savedInstanceState)
        initData()
        initViewModel()
        setListeners()
    }


    private fun initMainBinding() {
        baseBinding.baseMain.apply {
            addView(
                getBinding().root,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    private fun initViewStubToolbarBinding() {
        baseBinding.baseHead.layoutResource = R.layout.base_view_stub_toolbar
        baseToolbarBinding = BaseViewStubToolbarBinding.bind(baseBinding.baseHead.inflate())

    }

    private fun initViewStubLoading() {
        baseBinding.vsLoading.layoutResource = R.layout.base_view_stub_loading
        loadingView = baseBinding.vsLoading.inflate()
    }

    private fun initViewStubError() {
        baseBinding.vsError.layoutResource = R.layout.base_view_stub_error
        errorView = baseBinding.vsError.inflate()
    }

    override fun getBaseViewStatus(): BaseViewStatus? {
        return myBaseViewStatus
    }


    override fun onNewIntent(intent: Intent?) {
        intent!!.extras?.let { getBundleExtras(it) }
        super.onNewIntent(intent)
    }

    //设置监听
    protected open fun setListeners() {
        //empty implementation
    }

    override fun showLoadingLayout() {
        lottieLoadingView = loadingView.findViewById(R.id.lottie_loading_view)
        lottieLoadingView?.setAnimationFromUrl("https://assets9.lottiefiles.com/packages/lf20_on5CEa.json")
        lottieLoadingView?.repeatCount = ValueAnimator.INFINITE
        lottieLoadingView?.playAnimation()

        baseBinding.baseMain.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
        errorView.visibility = View.GONE

        lottieErrorView?.pauseAnimation()

        myBaseViewStatus = BaseViewStatus.LOADING
    }


    override fun showErrorLayout(errorMsg: String?) {
        baseBinding.baseMain.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        myBaseViewStatus = BaseViewStatus.ERROR

        lottieErrorView = errorView.findViewById(R.id.lottie_error_view)
        lottieErrorView?.setAnimationFromUrl("https://assets10.lottiefiles.com/packages/lf20_vzj1xd0x.json")
        lottieErrorView?.repeatCount = ValueAnimator.INFINITE
        lottieErrorView?.playAnimation()
        errorView.findViewById<TextView>(R.id.tv_reload).apply {
            setOnClickListener {
                initViewModel()
            }
        }

    }

    override fun showSuccessLayout() {
        baseBinding.baseMain.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE

        //暂停动画防止卡顿
        lottieLoadingView?.pauseAnimation()
        lottieErrorView?.pauseAnimation()

        //更改状态
        myBaseViewStatus = BaseViewStatus.SUCCESS
    }

    override fun setBaseViewStatus(baseViewStatus: BaseViewStatus?) {
        when (baseViewStatus) {
            BaseViewStatus.LOADING -> showLoadingLayout()
            BaseViewStatus.SUCCESS -> showSuccessLayout()
            else -> showErrorLayout("")
        }
    }

    override fun isBaseOnWidth(): Boolean {
        return true
    }

    override fun getSizeInDp(): Float {
        return 375F
    }


    override fun onDestroy() {
        AppManager.popActivity(this)
        super.onDestroy()
    }
}