package com.msc.demo_mvvm.component.photo_collage

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.flash.light.base.adapter.BaseAdapter
import com.msc.demo_mvvm.databinding.ItemPhotoBinding
import com.msc.demo_mvvm.domain.layer.Photo

class PhotoAdapter : BaseAdapter<Photo, ItemPhotoBinding>() {
    override fun provideViewBinding(parent: ViewGroup): ItemPhotoBinding {
        return ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun binData(viewBinding: ItemPhotoBinding, item: Photo) {
        super.binData(viewBinding, item)

        viewBinding.run {
            Glide.with(root.context).load(item.path).into(imv)
        }
    }
}