package com.zerosword.domain.reporitory

import com.zerosword.domain.model.GetPhotoModel


interface MainRepository {
    suspend fun getPhotos(
        onSuccess: (model: List<GetPhotoModel>) -> Unit,
        onError: (errorMessage: String) -> Unit
    )
}