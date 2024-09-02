package com.github.yuang.kt.android_mvvm.base

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.github.yuang.kt.android_mvvm.BaseApp
import com.github.yuang.kt.android_mvvm.R
import com.github.yuang.kt.android_mvvm.databinding.FragmentBaseBinding
import com.github.yuang.kt.android_mvvm.enmus.BaseViewStatus
import com.github.yuang.kt.android_mvvm.ext.showToast
import com.github.yuang.kt.android_mvvm.ext.startActivity
import com.github.yuang.kt.android_mvvm.interfaces.IBaseUIView
import com.github.yuang.kt.android_mvvm.utils.AppManager
import com.yk.loading.LoadingDialog

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */
abstract class BaseFragment : Fragment(), IBaseUIView {

    lateinit var mContext: Context
    private var isFirstLoad: Boolean = true //是否是第一次加载
    private lateinit var baseFragmentBaseBinding: FragmentBaseBinding
    private var myBaseViewStatus = BaseViewStatus.SUCCESS
    var loadingView: View? = null //加载中布局
    var errorView: View? = null //错误布局
    private var lottieErrorView: LottieAnimationView? = null
    private var lottieLoadingView: LottieAnimationView? = null
    private var dialog: LoadingDialog? = null
    private var lottieLoadingUrl: String =
        "lottie/loading-animation-blue.json"//加载动画
    private var lottieErrorUrl: String =
        "lottie/error-404.json"//失败动画


    /**
     * 设置加载动画
     */
    open fun setLottieLoadingUrl(lottieLoadingUrl: String) {
        this.lottieLoadingUrl = lottieLoadingUrl
    }

    /**
     * 设置失败动画
     */
    open fun setLottieErrorUrl(lottieErrorUrl: String) {
        this.lottieErrorUrl = lottieErrorUrl
    }

    abstract fun getBinding(): ViewBinding //绑定布局

    abstract fun initView(savedInstanceState: Bundle?) //初始化view

    abstract fun initData() //初始化数据

    abstract fun initViewModel()//数据请求

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isFirstLoad = true
        baseFragmentBaseBinding = FragmentBaseBinding.inflate(inflater, container, false)
        baseFragmentBaseBinding.vsLoading.layoutResource = R.layout.base_view_stub_loading
        loadingView = baseFragmentBaseBinding.vsLoading.inflate()
        baseFragmentBaseBinding.vsError.layoutResource = R.layout.base_view_stub_error
        errorView = baseFragmentBaseBinding.vsError.inflate()
        baseFragmentBaseBinding.baseMain.apply {
            addView(
                getBinding().root, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        baseFragmentBaseBinding.baseMain.visibility = View.VISIBLE
        baseFragmentBaseBinding.vsError.visibility = View.GONE
        baseFragmentBaseBinding.vsLoading.visibility = View.GONE

        return baseFragmentBaseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initData()
        initViewModel()
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    //设置监听
    protected open fun setListeners() {
        //empty implementation

    }

    override fun logOut() {
        BaseApp.instance.getLoginActivity()?.let {
            showToast("登录已失效,请重新登录")
            AppManager.getAppManager().finishAllActivity()
            val intent = Intent(mContext, it::class.java)
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

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


    override fun getBaseViewStatus(): BaseViewStatus? {
        return myBaseViewStatus
    }

    override fun setBaseViewStatus(baseViewStatus: BaseViewStatus?, lottieLoadingUrl: String?) {
        when (baseViewStatus) {
            BaseViewStatus.LOADING -> showLoadingLayout(lottieLoadingUrl)
            BaseViewStatus.SUCCESS -> showSuccessLayout()
            else -> showErrorLayout("")
        }
    }


    override fun showSuccessLayout() {
        baseFragmentBaseBinding.baseMain.visibility = View.VISIBLE
        baseFragmentBaseBinding.vsError.visibility = View.GONE
        baseFragmentBaseBinding.vsLoading.visibility = View.GONE

        //暂停动画防止卡顿
        lottieLoadingView?.pauseAnimation()
        lottieErrorView?.pauseAnimation()

        //更改状态
        myBaseViewStatus = BaseViewStatus.SUCCESS
    }

    override fun showLoadingLayout(lottieLoadingUrl: String?) {
        loadingView ?: let {
            lottieLoadingUrl?.let {
                lottieLoadingView = loadingView!!.findViewById(R.id.lottie_loading_view)
                lottieLoadingView!!.setAnimation(lottieLoadingUrl)
                lottieLoadingView!!.repeatCount = ValueAnimator.INFINITE
                lottieLoadingView!!.playAnimation()
            }
        }

        baseFragmentBaseBinding.baseMain.visibility = View.GONE
        baseFragmentBaseBinding.vsError.visibility = View.GONE
        baseFragmentBaseBinding.vsLoading.visibility = View.VISIBLE

        lottieErrorView?.pauseAnimation()

        myBaseViewStatus = BaseViewStatus.LOADING
    }

    override fun showErrorLayout(errorMsg: String?) {
        errorView?.let {
            lottieErrorView = errorView!!.findViewById(R.id.lottie_error_view)
            lottieErrorView!!.setAnimation(lottieErrorUrl)
            lottieErrorView!!.repeatCount = ValueAnimator.INFINITE
            lottieErrorView!!.playAnimation()

            errorView!!.findViewById<TextView>(R.id.tv_reload).apply {
                setOnClickListener {
                    initViewModel()
                }
            }
        }
        baseFragmentBaseBinding.baseMain.visibility = View.GONE
        baseFragmentBaseBinding.vsError.visibility = View.VISIBLE
        baseFragmentBaseBinding.vsLoading.visibility = View.GONE

        lottieLoadingView?.pauseAnimation()
        myBaseViewStatus = BaseViewStatus.ERROR
    }


    /**
     * 网络请求加载的中间弹窗
     */
    override fun showLoadingDialog(loadTxt: String?) {
        baseFragmentBaseBinding.baseMain.visibility = View.VISIBLE
        baseFragmentBaseBinding.vsError.visibility = View.GONE
        baseFragmentBaseBinding.vsLoading.visibility = View.GONE

        if (dialog == null) {
            val loadBuilder = LoadingDialog.Builder(mContext)
                .setMessage(loadTxt ?: "加载中...") //设置提示文字
                .setCancelable(true) //按返回键取消
                .setMessageColor(Color.WHITE) //提示文字颜色
                .setMessageSize(14) //提示文字字号
                .setBackgroundTransparent(true) //弹窗背景色是透明或半透明
                .setCancelOutside(true) //点击空白区域弹消失

            dialog = loadBuilder.create()
        }
        dialog?.show()
    }


    override fun dismissLoadingDialog(baseViewStatus: BaseViewStatus) {
        when (baseViewStatus) {
            BaseViewStatus.ERROR
            -> {
                baseFragmentBaseBinding.baseMain.visibility = View.GONE
                baseFragmentBaseBinding.vsError.visibility = View.VISIBLE
                baseFragmentBaseBinding.vsLoading.visibility = View.GONE
            }

            else -> {
                baseFragmentBaseBinding.baseMain.visibility = View.VISIBLE
                baseFragmentBaseBinding.vsError.visibility = View.GONE
                baseFragmentBaseBinding.vsLoading.visibility = View.GONE
            }
        }
        dialog?.dismiss()
    }
}