package com.github.yuang.kt.android_mvvm.widget.tab

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.github.yuang.kt.android_mvvm.R

class MsgView : AppCompatTextView {

    private var mContext: Context? = null

    private var backgroundColor = 0
    private var cornerRadius = 0
    private var strokeWidth = 0
    private var strokeColor = 0
    private var isRadiusHalfHeight = false
    private var isWidthHeightEqual = false
    private val gd_background = GradientDrawable()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.mContext = context
        obtainAttributes(context, attrs)
    }


    private fun obtainAttributes(context: Context, attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MsgView)
        backgroundColor = ta.getColor(R.styleable.MsgView_mv_backgroundColor, Color.TRANSPARENT)
        cornerRadius = ta.getDimensionPixelSize(R.styleable.MsgView_mv_cornerRadius, 0)
        strokeWidth = ta.getDimensionPixelSize(R.styleable.MsgView_mv_strokeWidth, 0)
        strokeColor = ta.getColor(R.styleable.MsgView_mv_strokeColor, Color.TRANSPARENT)
        isRadiusHalfHeight = ta.getBoolean(R.styleable.MsgView_mv_isRadiusHalfHeight, false)
        isWidthHeightEqual = ta.getBoolean(R.styleable.MsgView_mv_isWidthHeightEqual, false)

        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isWidthHeightEqual() && width > 0 && height > 0) {
            val max = Math.max(width, height)
            val measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isRadiusHalfHeight()) {
            setCornerRadius(height / 2)
        } else {
            setBgSelector()
        }
    }


    override fun setBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        setBgSelector()
    }


    fun setStrokeWidth(strokeWidth: Int) {
        this.strokeWidth = dp2px(strokeWidth.toFloat())
        setBgSelector()
    }

    fun setStrokeColor(strokeColor: Int) {
        this.strokeColor = strokeColor
        setBgSelector()
    }

    fun setIsRadiusHalfHeight(isRadiusHalfHeight: Boolean) {
        this.isRadiusHalfHeight = isRadiusHalfHeight
        setBgSelector()
    }

    fun setIsWidthHeightEqual(isWidthHeightEqual: Boolean) {
        this.isWidthHeightEqual = isWidthHeightEqual
        setBgSelector()
    }

    fun getBackgroundColor(): Int {
        return backgroundColor
    }

    fun getCornerRadius(): Int {
        return cornerRadius
    }

    fun getStrokeWidth(): Int {
        return strokeWidth
    }

    fun getStrokeColor(): Int {
        return strokeColor
    }


    fun isWidthHeightEqual(): Boolean {
        return isWidthHeightEqual
    }


    fun isRadiusHalfHeight(): Boolean {
        return isRadiusHalfHeight
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = dp2px(cornerRadius.toFloat())
        setBgSelector()
    }


    fun setBgSelector() {
        val bg = StateListDrawable()
        setDrawable(gd_background, backgroundColor, strokeColor)
        bg.addState(intArrayOf(-android.R.attr.state_pressed), gd_background)
        background = bg
    }

    private fun setDrawable(gd: GradientDrawable, color: Int, strokeColor: Int) {
        gd.setColor(color)
        gd.cornerRadius = cornerRadius.toFloat()
        gd.setStroke(strokeWidth, strokeColor)
    }

    private fun dp2px(dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun sp2px(sp: Float): Int {
        val scale = this.context.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }
}


