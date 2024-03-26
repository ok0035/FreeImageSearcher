package com.zerosword.feature_main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerosword.domain.reporitory.MainRepository
import com.zerosword.domain.model.GetPhotoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _photos: MutableStateFlow<List<GetPhotoModel?>?> = MutableStateFlow(null)
    val photos: StateFlow<List<GetPhotoModel?>?> get() = _photos

    private val _errorMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val errorMessage get() = _errorMessage

    init {
        viewModelScope.launch {
            getPhotos()
        }
    }

    private suspend fun getPhotos() = viewModelScope.launch {
        mainRepository.getPhotos(
            onSuccess = {
                _photos.value = it
            },
            onError = {
                viewModelScope.launch(Dispatchers.IO) {
                    _errorMessage.emit(it)
                }
            }
        )
    }
}