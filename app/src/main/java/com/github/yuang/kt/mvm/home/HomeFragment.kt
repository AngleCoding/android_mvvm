package com.github.yuang.kt.mvm.home

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.github.yuang.kt.android_mvvm.base.BaseFragment
import com.github.yuang.kt.mvm.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val HomeViewModel by lazy { HomeViewModel() }

    companion object {
        fun newInstance(id: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("id", id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getBinding(): ViewBinding {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {

    }

    override fun initViewModel() {
        HomeViewModel.searchEntForSampling()
    }
}