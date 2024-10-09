package com.msc.zoom10.domain.usecase

import com.msc.zoom10.data.repositories.FavouriteSoundRepository
import com.msc.zoom10.domain.layer.DetailsSound
import javax.inject.Inject

class UnFavouriteUseCase @Inject constructor(val favouriteSoundRepository: FavouriteSoundRepository) : UseCase<UnFavouriteUseCase.Param, Int>() {
    class Param(val detailsSound: DetailsSound) : UseCase.Param()

    override suspend fun execute(param: Param): Int {
        return favouriteSoundRepository.remove(param.detailsSound)
    }
}