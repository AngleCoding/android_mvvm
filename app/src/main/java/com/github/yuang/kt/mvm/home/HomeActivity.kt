package com.github.yuang.kt.mvm.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.github.yuang.kt.android_mvvm.adapter.FragmentAdapter
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.android_mvvm.sharedpreferences.LocalDataManager
import com.github.yuang.kt.mvm.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    override fun getBinding(): ViewBinding {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView(savedInstanceState: Bundle?) {
        baseToolbarBinding.toolbarTitle.text= "首页"
        val mTitles: ArrayList<String> = ArrayList()
        mTitles.add("推荐")
        mTitles.add("强档")
        mFragmentList.add(HomeFragment.newInstance(""))
        mFragmentList.add(HomeFragment.newInstance(""))
        binding.mViewPager.adapter = FragmentAdapter(supportFragmentManager, mFragmentList)
        binding.mViewPager.offscreenPageLimit = mFragmentList.size
        binding.tab.setViewPager(binding.mViewPager, mTitles)
        binding.tab.onPageSelected(0)
    }


    override fun initData() {
    }

    override fun initViewModel() {
    }
}