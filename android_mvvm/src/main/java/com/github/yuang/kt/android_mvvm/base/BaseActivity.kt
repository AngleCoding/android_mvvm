package com.github.yuang.kt.android_mvvm.base

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.amap.api.location.AMapLocationClient
import com.blankj.utilcode.util.LogUtils
import com.github.yuang.kt.android_mvvm.BaseApp
import com.github.yuang.kt.android_mvvm.R
import com.github.yuang.kt.android_mvvm.databinding.ActivityBaseBinding
import com.github.yuang.kt.android_mvvm.databinding.BaseViewStubToolbarBinding
import com.github.yuang.kt.android_mvvm.enmus.BaseViewStatus
import com.github.yuang.kt.android_mvvm.ext.initImmersionBar
import com.github.yuang.kt.android_mvvm.ext.showToast
import com.github.yuang.kt.android_mvvm.ext.startActivity
import com.github.yuang.kt.android_mvvm.interfaces.IBaseUIView
import com.github.yuang.kt.android_mvvm.utils.AmapLocationUtils
import com.github.yuang.kt.android_mvvm.utils.AppManager
import com.github.yuang.kt.android_mvvm.utils.DataStoreHelper
import com.jakewharton.rxbinding4.view.clicks
import com.yk.loading.LoadingDialog
import me.jessyan.autosize.internal.CustomAdapt
import java.util.concurrent.TimeUnit

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
    var loadingView: View? = null //加载中布局
    var errorView: View? = null //错误布局
    private var lottieErrorView: LottieAnimationView? = null
    private var lottieLoadingView: LottieAnimationView? = null
    private var loadingMsgView: TextView? = null
    private var loadingMsg: String = "加载中..."
    private val PERMISSION_REQUEST = 10086
    private var dialog: LoadingDialog? = null
    private var lottieLoadingUrl: String =
        "lottie/loading-animation-blue.json"//加载动画
    private var lottieErrorUrl: String =
        "lottie/error-404.json"//失败动画

    /**
     * 绑定布局
     */
    abstract fun getBinding(): ViewBinding

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 数据请求
     */
    abstract fun initViewModel()

    /**
     * 接收bundle数据
     */
    open fun getBundleExtras(extras: Bundle?) {}


    /**
     * 是否需要显示标题栏
     * 默认不显示
     */
    open fun isToolbarVisibility(): Boolean {
        return false
    }

    /**
     * 设置标题栏内容
     */
    open fun setTitleName(titleName: String) {
        baseToolbarBinding.toolbarTitle.text = titleName
    }

    /**
     * 设置标题栏右侧图片
     */
    open fun setRightIcon(@DrawableRes resId: Int) {
        baseToolbarBinding.rightIcon.setImageResource(resId)
    }

    /**
     * 设置标题栏右边文字内容
     */
    open fun setToolbarSubtitle(toolbarSubtitle: String) {
        baseToolbarBinding.toolbarSubtitle.text = toolbarSubtitle
    }

    /**
     * 设置标题栏返回图片
     */
    open fun setBackIcon(@DrawableRes resId: Int) {
        baseToolbarBinding.back.setImageResource(resId)
    }

    /**
     * 状态栏字体深色或亮色
     * 默认深色
     */
    open fun isDarkFont(): Boolean {
        return true
    }

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

    /**
     * 设置加载显示的文字
     */
    open fun setLoadingMsg(msg: String) {
        loadingMsg = msg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        intent.extras?.let { getBundleExtras(it) }
        init(savedInstanceState)
        AppManager.getAppManager().addActivity(this)
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
        initImmersionBar(this, baseBinding.statusBarView, isDarkFont())
        initViewStubToolbarBinding()
        initMainBinding()
        initView(savedInstanceState)
        initData()
        initViewModel()
        setListeners()
        initPermission()
    }


    private fun initMainBinding() {
        baseBinding.vsLoading.layoutResource = R.layout.base_view_stub_loading
        loadingView = baseBinding.vsLoading.inflate()

        baseBinding.vsError.layoutResource = R.layout.base_view_stub_error
        errorView = baseBinding.vsError.inflate()
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

        if (!isToolbarVisibility()) {
            baseToolbarBinding.mLlToolbar.visibility = View.GONE
            baseBinding.statusBarView.visibility = View.GONE
        } else {
            baseToolbarBinding.mLlToolbar.visibility = VISIBLE
            baseBinding.statusBarView.visibility = VISIBLE
        }
        baseToolbarBinding.toolbarTitle.isSelected = true
    }

    override fun getBaseViewStatus(): BaseViewStatus? {
        return myBaseViewStatus
    }


    override fun onNewIntent(intent: Intent?) {
        intent!!.extras?.let { getBundleExtras(it) }
        super.onNewIntent(intent)
    }

    //设置监听
    @SuppressLint("CheckResult")
    protected open fun setListeners() {
        //empty implementation
        baseToolbarBinding.back.clicks()
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                finish()
            }
        baseToolbarBinding.back.setOnClickListener { finish() }
    }


    override fun showLoadingLayout() {
        runOnUiThread {
            loadingView?.let {
                lottieLoadingView = loadingView!!.findViewById(R.id.lottie_loading_view)
                lottieLoadingView!!.setAnimation(lottieLoadingUrl)
                lottieLoadingView!!.repeatCount = ValueAnimator.INFINITE
                lottieLoadingView!!.playAnimation()
                loadingMsgView = loadingView!!.findViewById(R.id.tv_baseloadingmsg)
                loadingMsgView?.text = loadingMsg
            }
        }

        baseBinding.baseMain.visibility = View.GONE
        baseBinding.vsError.visibility = View.GONE
        baseBinding.vsLoading.visibility = VISIBLE


        myBaseViewStatus = BaseViewStatus.LOADING
    }

    override fun showErrorLayout(errorMsg: String?) {
        runOnUiThread {
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
        }
        baseBinding.baseMain.visibility = View.GONE
        baseBinding.vsError.visibility = VISIBLE
        baseBinding.vsLoading.visibility = View.GONE

        lottieLoadingView?.pauseAnimation()
        myBaseViewStatus = BaseViewStatus.ERROR
    }

    override fun showSuccessLayout() {
        baseBinding.baseMain.visibility = VISIBLE
        baseBinding.vsError.visibility = View.GONE
        baseBinding.vsLoading.visibility = View.GONE

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
        AppManager.getAppManager().finishActivity(this)
        if (this::class.simpleName.equals("MainActivity")) {
            AmapLocationUtils.getInstance().destroyLocation()
        }
        super.onDestroy()
    }

    private fun initPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PERMISSION_REQUEST
        )
    }


    /**
     * 网络请求加载的中间弹窗
     */
    override fun showLoadingDialog() {
        if (dialog == null) {
            val loadBuilder = LoadingDialog.Builder(mContext)
                .setMessage("加载中...") //设置提示文字
                .setCancelable(false) //按返回键取消
                .setMessageColor(Color.WHITE) //提示文字颜色
                .setMessageSize(14) //提示文字字号
                .setBackgroundTransparent(false) //弹窗背景色是透明或半透明
                .setCancelOutside(false) //点击空白区域弹消失

            dialog = loadBuilder.create()
        }
        dialog?.show()
    }

    override fun logOut() {
        BaseApp.instance.getLoginActivity()?.let {
            showToast("登录已失效,请重新登录")
            AppManager.getAppManager().finishAllActivity()
            val intent = Intent(this, it::class.java)
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun dismissLoadingDialog(baseViewStatus: BaseViewStatus) {
        when (baseViewStatus) {
            BaseViewStatus.ERROR
            -> {
                baseBinding.baseMain.visibility = View.GONE
                baseBinding.vsError.visibility = VISIBLE
                baseBinding.vsLoading.visibility = View.GONE
            }

            else -> {
                baseBinding.baseMain.visibility = VISIBLE
                baseBinding.vsError.visibility = View.GONE
                baseBinding.vsLoading.visibility = View.GONE
            }
        }
        dialog?.dismiss()
    }
}

