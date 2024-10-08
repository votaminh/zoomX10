package com.msc.demo_mvvm.component.photo_collage

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msc.demo_mvvm.R
import com.msc.demo_mvvm.base.activity.BaseActivity
import com.msc.demo_mvvm.databinding.ActivityPhotoCollageBinding
import com.msc.demo_mvvm.utils.ViewEx.invisible
import com.msc.demo_mvvm.utils.ViewEx.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoCollageActivity : BaseActivity<ActivityPhotoCollageBinding>() {

    private val viewModel: PhotoCollageViewModel by viewModels()
    private val photoAdapter = PhotoAdapter()

    companion object {
        fun start(activity : Activity){
            activity.startActivity(Intent(activity, PhotoCollageActivity::class.java))
        }
    }

    override fun provideViewBinding(): ActivityPhotoCollageBinding {
        return ActivityPhotoCollageBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setStatusBarColor(ContextCompat.getColor(this@PhotoCollageActivity, R.color.white), true)
        buildRePhotos()
        viewModel.getListPhoto()

        viewBinding.imvBack.setOnClickListener {
            finish()
        }
    }

    private fun buildRePhotos() {
        viewBinding.rePhotos.run {
            layoutManager = GridLayoutManager(this@PhotoCollageActivity, 4, RecyclerView.VERTICAL, false)
            adapter = photoAdapter
        }
    }

    override fun initObserver() {
        super.initObserver()

        viewModel.photoListLive.observe(this){
            if(it.isEmpty()){
                viewBinding.llNoImage.visible()
                viewBinding.rePhotos.invisible()
            }else{
                viewBinding.llNoImage.invisible()
                viewBinding.rePhotos.visible()
                photoAdapter.setData(it)
            }
        }
    }
}