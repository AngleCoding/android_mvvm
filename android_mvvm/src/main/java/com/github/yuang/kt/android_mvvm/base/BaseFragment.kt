package com.github.yuang.kt.android_mvvm.base

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.github.yuang.kt.android_mvvm.R
import com.github.yuang.kt.android_mvvm.databinding.FragmentBaseBinding
import com.github.yuang.kt.android_mvvm.enmus.BaseViewStatus
import com.github.yuang.kt.android_mvvm.interfaces.IBaseUIView
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
    private lateinit var loadingView: View
    private lateinit var errorView: View
    private var lottieErrorView: LottieAnimationView? = null
    private var lottieLoadingView: LottieAnimationView? = null
    private var dialog: LoadingDialog? = null

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
        baseFragmentBaseBinding.vsError.layoutResource = R.layout.base_view_stub_error
        return baseFragmentBaseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseFragmentBaseBinding.baseMain.apply {
            addView(
                getBinding().root, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
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

    override fun setBaseViewStatus(baseViewStatus: BaseViewStatus?) {
        when (baseViewStatus) {
            BaseViewStatus.LOADING -> showLoadingLayout()
            BaseViewStatus.SUCCESS -> showSuccessLayout()
            else -> showErrorLayout("")
        }
    }

    override fun showSuccessLayout() {
        baseFragmentBaseBinding.baseMain.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE

        //暂停动画防止卡顿
        lottieLoadingView?.pauseAnimation()
        lottieErrorView?.pauseAnimation()

        //更改状态
        myBaseViewStatus = BaseViewStatus.SUCCESS
    }

    override fun showLoadingLayout() {
        loadingView = baseFragmentBaseBinding.vsLoading.inflate()
        lottieLoadingView = loadingView.findViewById(R.id.lottie_loading_view)
        lottieLoadingView?.setAnimationFromUrl("https://assets9.lottiefiles.com/packages/lf20_on5CEa.json")
        lottieLoadingView?.repeatCount = ValueAnimator.INFINITE
        lottieLoadingView?.playAnimation()

        baseFragmentBaseBinding.baseMain.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
        errorView.visibility = View.GONE

        lottieErrorView?.pauseAnimation()

        myBaseViewStatus = BaseViewStatus.LOADING
    }

    override fun showErrorLayout(errorMsg: String?) {
        errorView = baseFragmentBaseBinding.vsError.inflate()
        baseFragmentBaseBinding.baseMain.visibility = View.GONE
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


    override fun dismissLoadingDialog() {
        dialog?.dismiss()
    }
}