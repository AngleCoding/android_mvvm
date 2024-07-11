package com.github.yuang.kt.mvm.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.github.yuang.kt.android_mvvm.base.BaseFragment
import com.github.yuang.kt.android_mvvm.ext.click
import com.github.yuang.kt.android_mvvm.ext.showToast
import com.github.yuang.kt.android_mvvm.ext.vmObserverDefault
import com.github.yuang.kt.android_mvvm.ext.vmObserverLoading
import com.github.yuang.kt.android_mvvm.ext.vmObserverMain
import com.github.yuang.kt.mvm.adapter.SearchEntForSamplingAdapter
import com.github.yuang.kt.mvm.databinding.FragmentHomeBinding
import com.github.yuang.kt.mvm.main.MainViewModel
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val mainViewModel by lazy { MainViewModel() }

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
        mainViewModel.refreshData.vmObserverMain(this,
            onSuccess = {
            }
        )
    }


    override fun initViewModel() {
    }


    override fun setListeners() {
        super.setListeners()

        binding.mBtLogin.click {
            mainViewModel.login()
        }
    }



}