package com.zerosword.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zerosword.data.paging.ImagePagingSource
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

    override fun getPhotos(keyword: String): Flow<PagingData<PhotoModel>> {
        return Pager(
            config = PagingConfig(pageSize = 999, enablePlaceholders = false),
            pagingSourceFactory = { ImagePagingSource(keword = keyword, apiService = mainService) }
        ).flow
    }


}