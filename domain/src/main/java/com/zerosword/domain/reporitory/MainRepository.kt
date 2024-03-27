package com.zerosword.domain.reporitory

import androidx.paging.PagingData
import com.zerosword.domain.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query


interface MainRepository {
    fun getPhotos(keyword: String): Flow<PagingData<PhotoModel>>

//    suspend fun searchPhotos(
//        keword: String = "",
//        page: Int = 1,
//        perPage: Int = 20,
//        orderBy: String = "relevant",
//    ) : Flow<PagingData<PhotoModel>>
}