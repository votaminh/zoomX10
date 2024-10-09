package com.msc.zoom10.domain.usecase

import android.content.Context
import com.msc.zoom10.domain.layer.Photo
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class GetAllPhotoUseCase  @Inject constructor(@ApplicationContext val context: Context) : UseCase<GetAllPhotoUseCase.Param, List<Photo>>() {

    open class Param() : UseCase.Param()

    override suspend fun execute(param: Param): List<Photo> {
        val photoFolder = File(context.filesDir, "photo")
        if(!photoFolder.exists()){
            photoFolder.mkdirs()
        }
        return photoFolder.listFiles()?.map { Photo(it.path, it.lastModified()) } ?: listOf()
    }
}