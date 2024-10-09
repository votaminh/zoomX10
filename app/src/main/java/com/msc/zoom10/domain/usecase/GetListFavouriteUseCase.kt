package com.msc.zoom10.domain.usecase

import com.msc.zoom10.data.repositories.FavouriteSoundRepository
import com.msc.zoom10.domain.layer.DetailsSound
import javax.inject.Inject

class GetListFavouriteUseCase @Inject constructor(private val favouriteSoundRepository: FavouriteSoundRepository)
    : UseCase<GetListFavouriteUseCase.Param, List<DetailsSound>>() {
    class Param : UseCase.Param()

    override suspend fun execute(param: Param): List<DetailsSound> {
        return favouriteSoundRepository.getList()
    }
}