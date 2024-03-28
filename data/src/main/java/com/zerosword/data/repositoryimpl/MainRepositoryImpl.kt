package com.zerosword.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.zerosword.data.paging.ImagePagingSource
import com.zerosword.data.response.toDomainModel
import com.zerosword.data.services.MainService
import com.zerosword.domain.model.PhotoModel
import com.zerosword.domain.reporitory.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService
) : MainRepository {

    override suspend fun getPhotos(
        keyword: String,
        page: Int,
        perPage: Int,
        onError: (message: String) -> Unit,
        onSuccess: (List<PhotoModel>) -> Unit
    ) {
        mainService.searchPhotos(keyword, page, perPage).suspendOnSuccess {
            onSuccess(data.toDomainModel())
        }.suspendOnFailure {
            onError(message())
        }
    }


}