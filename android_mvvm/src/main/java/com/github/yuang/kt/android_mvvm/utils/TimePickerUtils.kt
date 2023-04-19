package com.github.yuang.kt.android_mvvm.utils

import android.R
import android.content.Context
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bigkoo.pickerview.builder.TimePickerBuilder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimePickerUtils {

    fun getStringTimeOfYMD(currentTimeMillis: Long): String {
        val date = Date(currentTimeMillis)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf
            .format(date)
    }
    interface TimeSelectData {
        fun callback(data: String?)
    }

    fun TimePickerShow(context: Context, selectListener: TimeSelectData?) {
        val startDate = Calendar.getInstance()
        startDate[2017, 0] = 1
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        TimePickerBuilder(context) { date, v ->
            if (selectListener != null) {
                selectListener.callback(getDateStr(date, null))
            }
        }.setType(booleanArrayOf(true, true, true, false, false, false)) // 默认全部显示
            .setCancelText("取消") //取消按钮文字
            .setSubmitText("确定") //确认按钮文字
            .setSubCalSize(14)
            .setContentTextSize(16) //滚轮文字大小
            .setTitleSize(14) //标题文字大小
            .setTitleText("") //标题文字
            .setOutSideCancelable(true) //点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(false) //是否循环滚动
            .setDate(calendar) // 如果不设置的话，默认是系统时间*/
            .setRangDate(startDate, calendar) //起始终止年月日设定
            .setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .isDialog(false) //是否显示为对话框样式
            .setDecorView((context as AppCompatActivity).window.decorView.findViewById(R.id.content))
            .build().show()
    }
    @JvmStatic
    fun TimePickerShow(window: Window, selectListener: TimeSelectData?) {
        val startDate = Calendar.getInstance()
        startDate[2017, 0] = 1
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        TimePickerBuilder(window.context) { date: Date?, v: View? ->
            selectListener?.callback(getDateStr(date, null))
        }.setType(booleanArrayOf(true, true, true, false, false, false)) // 默认全部显示
            .setCancelText("取消") //取消按钮文字
            .setSubmitText("确定") //确认按钮文字
            .setSubCalSize(14)
            .setContentTextSize(16) //滚轮文字大小
            .setTitleSize(14) //标题文字大小
            .setTitleText("") //标题文字
            .setOutSideCancelable(true) //点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(false) //是否循环滚动
            .setDate(calendar) // 如果不设置的话，默认是系统时间*/
            .setRangDate(startDate, calendar) //起始终止年月日设定
            .setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .isDialog(false) //是否显示为对话框样式
            .setDecorView(window.decorView.findViewById(R.id.content)).build().show()
    }

    @JvmStatic
    fun TimePickerShow2(window: Window, selectListener: TimeSelectData?) {
        val startDate = Calendar.getInstance()
        startDate[2017, 0] = 1
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        TimePickerBuilder(window.context) { date: Date?, v: View? ->
            selectListener?.callback(getDateStr2(date, null))
        }.setType(booleanArrayOf(true, true, true, true, true, true)) // 默认全部显示
            .setCancelText("取消") //取消按钮文字
            .setSubmitText("确定") //确认按钮文字
            .setSubCalSize(14)
            .setContentTextSize(16) //滚轮文字大小
            .setTitleSize(14) //标题文字大小
            .setTitleText("") //标题文字
            .setOutSideCancelable(true) //点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(false) //是否循环滚动
            .setDate(calendar) // 如果不设置的话，默认是系统时间*/
            .setRangDate(startDate, calendar) //起始终止年月日设定
            .setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .isDialog(false) //是否显示为对话框样式
            .setDecorView(window.decorView.findViewById(R.id.content)).build().show()
    }

    fun getDateStr(date: Date?, format: String?): String? {
        var format = format
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd "
        }
        val formatter = SimpleDateFormat(format)
        return formatter.format(date)
    }
    fun getDateStr2(date: Date?, format: String?): String? {
        var format = format
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss"
        }
        val formatter = SimpleDateFormat(format)
        return formatter.format(date)
    }
    fun getStringToDate(time: String?): Long {
        val sf = SimpleDateFormat("yyyy-MM-dd")
        var date = Date()
        try {
            date = sf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date.time
    }

}