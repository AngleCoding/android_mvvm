package com.github.yuang.kt.mvm.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.github.yuang.kt.android_mvvm.base.BaseFragment
import com.github.yuang.kt.android_mvvm.ext.vmObserverDefault
import com.github.yuang.kt.android_mvvm.ext.vmObserverLoading
import com.github.yuang.kt.mvm.adapter.SearchEntForSamplingAdapter
import com.github.yuang.kt.mvm.databinding.FragmentHomeBinding
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

class HomeFragment : BaseFragment(), OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding

    private val HomeViewModel by lazy { HomeViewModel() }

    private lateinit var adapter: SearchEntForSamplingAdapter

    private var helper: QuickAdapterHelper? = null

    private var pageNumber: Int = 1

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
        binding.mSmartRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))
        binding.mSmartRefreshLayout.setOnRefreshListener(this)
        initAdapter()
    }

    private fun initAdapter() {
        adapter = SearchEntForSamplingAdapter()
        helper = QuickAdapterHelper.Builder(adapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onFailRetry() {
                    HomeViewModel.searchEntForSamplingF()
                }

                override fun onLoad() {
                    HomeViewModel.searchEntForSamplingMore()
                }

                override fun isAllowLoading(): Boolean {
                    return true
                }


            }).build()

        binding.mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        binding.mRecyclerView.adapter = helper?.adapter
    }


    override fun initData() {
        initRefreshData()
    }

    private fun initRefreshData() {

        HomeViewModel.refreshData.vmObserverLoading(this,
            onSuccess = {
                pageNumber = it.result.pager.pageNumber
                if (it.result.pager.pageNumber == 1) {
                    adapter.submitList(it.result.list)
                } else {
                    adapter.addAll(it.result.list)
                }

                if (it.result.pager.pageNumber >= it.result.pager.recordCount) {
                    helper?.trailingLoadState = LoadState.NotLoading(true)
                } else {
                    helper?.trailingLoadState = LoadState.NotLoading(false)
                }
            }, onComplete = {
                binding.mSmartRefreshLayout.finishRefresh()
            }
        )

    }

    override fun initViewModel() {
        HomeViewModel.searchEntForSamplingF()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        HomeViewModel.searchEntForSamplingF()
    }

}