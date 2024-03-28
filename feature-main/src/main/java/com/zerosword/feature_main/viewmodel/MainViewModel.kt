package com.zerosword.feature_main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerosword.domain.reporitory.MainRepository
import com.zerosword.domain.model.PhotoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Collections.addAll
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val currentList: MutableStateFlow<List<PhotoModel>> = MutableStateFlow(listOf())
    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("Cat")
    private val page: MutableStateFlow<Int> = MutableStateFlow(1)
    private val errorMessage: MutableSharedFlow<String> = MutableSharedFlow()

    private var photos: MutableStateFlow<List<PhotoModel>> = MutableStateFlow(listOf())
    private var loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        searchQuery("random")
    }

    fun searchQuery(query: String) = viewModelScope.launch {
        searchQuery.value = query
        page.value = 1
        mainRepository.getPhotos(keyword = query, page = 1, perPage = 50,
            onError = {
                viewModelScope.launch {
                    errorMessage.emit(it)
                }
            },
            onSuccess = {
                currentList.value = it
            }
        )
    }

    fun nextPage() = viewModelScope.launch {
        if(loading.value) return@launch

        loading.value = true
        page.value = page.value + 1
        mainRepository.getPhotos(searchQuery.value, page.value, 50,
            onSuccess = {
                if(it.isEmpty()) return@getPhotos
                val curList = currentList.value.toMutableList()
                curList.addAll(it)
                currentList.value = curList
                loading.value = false
            },
            onError = {
                viewModelScope.launch {
                    errorMessage.emit(it)
                    loading.value = false
                }
            }
        )
    }

    fun searchPhotos(
        keword: String,
        page: Int = 1,
        perPage: Int = 50,
    ) = viewModelScope.launch {
        mainRepository.getPhotos(
            keword, page, perPage,
            onSuccess = {
                photos.value = it
            }, onError = {
                viewModelScope.launch(Dispatchers.IO) {
                    errorMessage.emit(it)
                }
            }
        )
    }
}