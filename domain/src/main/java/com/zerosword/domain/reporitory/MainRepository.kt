package com.zerosword.domain.reporitory

import androidx.paging.PagingData
import com.zerosword.domain.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query


interface MainRepository {
    suspend fun getPhotos(
        keyword: String,
        page: Int,
        perPage: Int,
        onError: (message: String) -> Unit,
        onSuccess: (List<PhotoModel>) -> Unit
    )

//    suspend fun searchPhotos(
//        keword: String = "",
//        page: Int = 1,
//        perPage: Int = 20,
//        orderBy: String = "relevant",
//    ) : Flow<PagingData<PhotoModel>>
}