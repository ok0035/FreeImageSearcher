package com.zerosword.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydoves.sandwich.getOrNull
import com.zerosword.data.response.toDomainModel
import com.zerosword.data.services.MainService
import com.zerosword.domain.model.PhotoModel

class ImagePagingSource(
    private val apiService: MainService,
    private val keword: String
) : PagingSource<Int, PhotoModel>() {

    private val startingPageIdx = 1
    private var pageNumber = startingPageIdx
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoModel> {
        return try {
            pageNumber = params.key ?: 1
            val model =
                if (keword.isNotEmpty()) apiService.searchPhotos(keword = keword, page = pageNumber)
                    .getOrNull()?.toDomainModel()
                else apiService.getPhotos(page = pageNumber).getOrNull()?.toDomainModel()

            if(model == null) return LoadResult.Page(listOf(), pageNumber - 1, pageNumber + 1)

            LoadResult.Page(
                data = model,
                prevKey = if (pageNumber == startingPageIdx) null else pageNumber - 1,
                nextKey = if (model.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoModel>): Int {
        return pageNumber
    }
}