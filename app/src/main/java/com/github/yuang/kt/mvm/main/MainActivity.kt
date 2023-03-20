package com.github.yuang.kt.mvm.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.android_mvvm.ext.showToast
import com.github.yuang.kt.android_mvvm.ext.vmObserverMain
import com.github.yuang.kt.mvm.databinding.ActivityMainBinding
import com.yalantis.ucrop.UCropActivity

class MainActivity : BaseActivity() {
    private val mainViewModel by lazy { MainViewModel() }
    private lateinit var mainBinding: ActivityMainBinding

    override fun getBinding(): ViewBinding {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        return mainBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        mainViewModel.refreshData.vmObserverMain(this,
            onSuccess = {
                showToast(it.result.category.toString())
            }
        )

    }

    override fun initViewModel() {
        mainViewModel.login()
    }

    override fun setListeners() {
        super.setListeners()
        mainBinding.mBtLogin.setOnClickListener {
            mainViewModel.login()
        }
    }

}