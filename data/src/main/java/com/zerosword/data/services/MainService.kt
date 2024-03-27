package com.zerosword.data.services

import com.skydoves.sandwich.ApiResponse
import com.zerosword.data.BuildConfig
import com.zerosword.data.response.GetPhotosRes
import com.zerosword.data.response.SearchPhotoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MainService {

    /*
    * API 작성
    * 반드시 suspend function 으로 만들어야 ApiResponse를 사용할 수 있습니다.
    * */
    @GET("photos")
    @Headers("Authorization: Client-ID ${BuildConfig.unsplashApiAccessKey}")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50,
        @Query("order_by") orderBy: String = "popular"
    ): ApiResponse<GetPhotosRes>

    @GET("search/photos")
    @Headers("Authorization: Client-ID ${BuildConfig.unsplashApiAccessKey}")
    suspend fun searchPhotos(
        @Query("query") keword: String = "",
        @Query("page") page: Int = 50,
        @Query("per_page") perPage: Int = 50,
        @Query("order_by") orderBy: String = "relevant",
    ): ApiResponse<SearchPhotoResponse>

}