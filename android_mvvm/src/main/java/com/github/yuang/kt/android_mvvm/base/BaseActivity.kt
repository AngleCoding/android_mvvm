package com.github.yuang.kt.android_mvvm.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.github.yuang.kt.android_mvvm.R
import com.github.yuang.kt.android_mvvm.databinding.ActivityBaseBinding
import com.github.yuang.kt.android_mvvm.databinding.BaseViewStubToolbarBinding
import com.github.yuang.kt.ext.initImmersionBar
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseActivity : AppCompatActivity(), CustomAdapt {

    lateinit var mContext: Context
    private lateinit var baseBinding: ActivityBaseBinding
    lateinit var baseToolbarBinding: BaseViewStubToolbarBinding//标题栏

    abstract fun getBinding(): ViewBinding //绑定布局

    abstract fun initView(savedInstanceState: Bundle?) //初始化view

    abstract fun initData() //初始化数据

    open fun getBundleExtras(extras: Bundle?) {} //接收bundle数据


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        intent.extras?.let { getBundleExtras(it) }
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        initImmersionBar(this, baseBinding.statusBarView)
        initViewStubToolbarBinding()
        initMainBinding()
        initView(savedInstanceState)

    }

    private fun initMainBinding() {
        baseBinding.baseMain.apply {
            addView(
                getBinding().root, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    private fun initViewStubToolbarBinding() {
        baseBinding.baseHead.layoutResource = R.layout.base_view_stub_toolbar
        baseToolbarBinding = BaseViewStubToolbarBinding.bind(baseBinding.baseHead.inflate())

    }

    override fun onNewIntent(intent: Intent?) {
        intent!!.extras?.let { getBundleExtras(it) }
        super.onNewIntent(intent)
    }

    override fun isBaseOnWidth(): Boolean {
        return true
    }

    override fun getSizeInDp(): Float {
        return 375F
    }

}