package com.msc.zoom10.domain.usecase

import com.msc.zoom10.data.repositories.FavouriteSoundRepository
import com.msc.zoom10.domain.layer.DetailsSound
import javax.inject.Inject

class AddFavouriteUseCase @Inject constructor(val favouriteSoundRepository: FavouriteSoundRepository) : UseCase<AddFavouriteUseCase.Param, Long>() {
    class Param(val detailsSound: DetailsSound) : UseCase.Param()

    override suspend fun execute(param: Param): Long {
        return favouriteSoundRepository.insert(param.detailsSound)
    }
}