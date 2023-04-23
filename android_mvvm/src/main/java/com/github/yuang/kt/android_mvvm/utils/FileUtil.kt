package com.github.yuang.kt.android_mvvm.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import java.io.FileNotFoundException
import java.io.OutputStream


object FileUtil {

    fun setViewHeight(view: View, width: Int, height: Int) {
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams ?: return
        layoutParams.width = width
        layoutParams.height = height
        view.layoutParams = layoutParams
    }

    @Throws(FileNotFoundException::class)
    fun saveBitmap(context: Context, bitmap: Bitmap?): Boolean {
        if (bitmap != null) {
            val addPictureToAlbum =
                addPictureToAlbum(context, bitmap, "GSY-" + System.currentTimeMillis() + ".jpg")
            bitmap.recycle()

            return addPictureToAlbum
        }
        return false
    }

    /**
     * 保存图片到picture 目录，Android Q适配，最简单的做法就是保存到公共目录，不用SAF存储
     *
     * @param context
     * @param bitmap
     * @param fileName
     */
    fun addPictureToAlbum(context: Context, bitmap: Bitmap, fileName: String?): Boolean {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, fileName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        val uri: Uri? = context.contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        val outputStream: OutputStream?
        try {
            outputStream = uri?.let { context.contentResolver.openOutputStream(it) }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

}