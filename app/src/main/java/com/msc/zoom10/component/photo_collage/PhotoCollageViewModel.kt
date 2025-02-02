package com.msc.zoom10.component.photo_collage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msc.zoom10.domain.layer.Photo
import com.msc.zoom10.domain.usecase.GetAllPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoCollageViewModel @Inject constructor(
    val getAllPhotoUseCase: GetAllPhotoUseCase
) : ViewModel() {

    val photoListLive = MutableLiveData<List<Photo>>()

    fun getListPhoto() {
        viewModelScope.launch {
            photoListLive.postValue(getAllPhotoUseCase.execute(GetAllPhotoUseCase.Param()))
        }
    }
}