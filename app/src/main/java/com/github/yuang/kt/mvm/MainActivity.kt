package com.github.yuang.kt.mvm

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.mvm.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun getBinding(): ViewBinding {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        return mainBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
    }


    override fun initData() {
    }


}