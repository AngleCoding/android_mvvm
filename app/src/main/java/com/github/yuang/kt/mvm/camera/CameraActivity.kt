package com.github.yuang.kt.mvm.camera

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.github.yuan.picture_take.FullyGridLayoutManager
import com.github.yuan.picture_take.GridImageAdapter
import com.github.yuan.picture_take.PictureChooseDialog
import com.github.yuan.picture_take.config.SelectMimeType
import com.github.yuan.picture_take.entity.LocalMedia
import com.github.yuan.picture_take.interfaces.OnResultCallbackListener
import com.github.yuang.kt.android_mvvm.base.BaseActivity
import com.github.yuang.kt.mvm.databinding.ActivityCameraBinding
import java.util.ArrayList

class CameraActivity : BaseActivity(), GridImageAdapter.AddPicClickListener {

    private lateinit var cameraBinding: ActivityCameraBinding
    private lateinit var adapter: GridImageAdapter

    override fun getBinding(): ViewBinding {
        cameraBinding = ActivityCameraBinding.inflate(layoutInflater)
        return cameraBinding
    }

    override fun initView(savedInstanceState: Bundle?) {
        baseToolbarBinding.toolbarTitle.text = "多图上传"
        adapter = GridImageAdapter(mContext)
        adapter.setAddPicClickListener(this)
        cameraBinding.mRecyclerView.layoutManager = FullyGridLayoutManager(mContext, 4)
        cameraBinding.mRecyclerView.adapter = adapter
    }

    override fun initData() {
    }

    override fun initViewModel() {

    }

    override fun onAddPicClick(position: Int) {
        PictureChooseDialog.build(this) {
            setSingleOrMutableMode(true)//开启多图模式
            setMaxSelectNum(2)//最大数量
            openGalleryChooseMode(SelectMimeType.TYPE_ALL)//多图模式-相册
            openCameraChooseMode(SelectMimeType.TYPE_ALL)//多图模式-相机
            setSelectedData(adapter.data)// 相册已选数据
            setImageMutableForResult(object : OnResultCallbackListener<LocalMedia> {
                //多图模式-选择相册回显数据
                override fun onResult(result: ArrayList<LocalMedia>) {
                    adapter.setList(result)
                }

                override fun onCancel() {
                }

            })
            setCameraMutableForResult(object : OnResultCallbackListener<LocalMedia> {
                //多图模式-选择相机回显数据
                override fun onResult(result: ArrayList<LocalMedia>) {
                    adapter.setList(result)
                }

                override fun onCancel() {
                }

            })

            show()
        }
    }


}