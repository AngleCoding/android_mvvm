package com.github.yuang.kt.mvm.main

import android.os.Bundle
import android.view.View.GONE
import androidx.viewbinding.ViewBinding
import com.github.yuan.picture_take.PictureChooseDialog
import com.github.yuan.picture_take.utils.GlideApp
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.android_mvvm.ext.showToast
import com.github.yuang.kt.android_mvvm.ext.vmObserverMain
import com.github.yuang.kt.mvm.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val mainViewModel by lazy { MainViewModel() }
    private lateinit var mainBinding: ActivityMainBinding

    override fun getBinding(): ViewBinding {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        return mainBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        baseToolbarBinding.toolbarTitle.text="登录"
    }

    override fun initData() {
        mainViewModel.refreshData.vmObserverMain(this,
            onSuccess = {
                showToast("登录成功")
                baseToolbarBinding.toolbarTitle.text="GitHub主页"
                mainBinding.mWebView.loadUrl("https://github.com/AnglePengCoding/android_mvvm")
                mainBinding.mBtLogin.visibility= GONE
            }
        )

    }

    override fun initViewModel() {
        mainViewModel.login()
    }

    override fun setListeners() {
        super.setListeners()

        baseToolbarBinding.mLlToolbar.setOnClickListener {
            mainViewModel.login()
        }
    }

}