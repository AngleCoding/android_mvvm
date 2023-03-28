package com.github.yuang.kt.android_mvvm.widget.pager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ViewPager(context, attrs) {

    private var noScroll = false //true 代表不能滑动 //false 代表能滑动

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }


    override fun onTouchEvent(arg0: MotionEvent?): Boolean {
        return if (noScroll) false else super.onTouchEvent(arg0)
    }


    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {
        return if (noScroll) false else super.onInterceptTouchEvent(arg0)
    }


    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }


    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false) //表示切换的时候，不需要切换时间。
    }


}