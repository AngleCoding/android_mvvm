package com.github.yuang.kt.mvm.login

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.mvm.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun getBinding(): ViewBinding {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding
    }


    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }


    override fun initViewModel() {
    }
}