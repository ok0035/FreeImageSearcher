package com.zerosword.data.repositoryimpl

import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.zerosword.data.mapper.toDomainModel
import com.zerosword.data.services.MainService
import com.zerosword.domain.model.GetPhotoModel
import com.zerosword.domain.reporitory.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService
) : MainRepository {

    override suspend fun getPhotos(
        onSuccess: (res: List<GetPhotoModel>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        mainService.getPhotos()
            .suspendOnSuccess {
                onSuccess(this.data.toDomainModel())
            }.suspendOnFailure {
                onError(this.message())
            }
    }

}