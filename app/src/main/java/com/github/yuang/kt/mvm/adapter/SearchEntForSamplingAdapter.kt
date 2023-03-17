package com.github.yuang.kt.mvm.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.github.yuang.kt.mvm.R
import com.github.yuang.kt.mvm.entity.ListBean

class SearchEntForSamplingAdapter : BaseQuickAdapter<ListBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: ListBean?) {
        holder.setText(R.id.mTvName, item?.eName)
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_searchent_for_sampling, parent)
    }

}