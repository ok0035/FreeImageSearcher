package com.zerosword.feature_main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.zerosword.data.response.toDomainModel
import com.zerosword.domain.reporitory.MainRepository
import com.zerosword.domain.model.PhotoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("Cat")
    val searchQuery : StateFlow<String> get() = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class)
    val photos = _searchQuery.flatMapLatest { query ->
        mainRepository.getPhotos(query)
    }.cachedIn(viewModelScope)

    private val _errorMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val errorMessage get() = _errorMessage

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

//    private suspend fun getPhotos() = viewModelScope.launch {
//        mainRepository.getPhotos(
//            onSuccess = {
//                _photos.value = it
//            },
//            onError = {
//                viewModelScope.launch(Dispatchers.IO) {
//                    _errorMessage.emit(it)
//                }
//            }
//        )
//    }
//
//    fun searchPhotos(
//        keword: String,
//        page: Int = 1,
//        perPage: Int = 50,
//    ) = viewModelScope.launch {
//        mainRepository.searchPhotos(
//            keword, page, perPage,
//            onSuccess = {
//                _photos.value = it
//            }, onError = {
//                viewModelScope.launch(Dispatchers.IO) {
//                    _errorMessage.emit(it)
//                }
//            }
//        )
//    }
}