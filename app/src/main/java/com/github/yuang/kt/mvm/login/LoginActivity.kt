package com.github.yuang.kt.mvm.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LogUtils
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.mvm.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun getBinding(): ViewBinding {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding
    }


    override fun isToolbarVisibility(): Boolean {
        setTitleName("农产品抽样")
        return true
    }

    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }


    override fun initViewModel() {
    }
}