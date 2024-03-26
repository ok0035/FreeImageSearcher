package com.zerosword.data.mapper

import com.zerosword.data.response.GetPhotosRes
import com.zerosword.domain.model.GetPhotoModel

fun GetPhotosRes.toDomainModel(): List<GetPhotoModel> {
    val urlList = mutableListOf<GetPhotoModel>()
    this.forEach {
        urlList.add(
            GetPhotoModel(
                full = it.urls?.full,
                raw = it.urls?.raw,
                regular = it.urls?.regular,
                small = it.urls?.small,
                thumb = it.urls?.thumb
            )
        )
    }
    return urlList
}

