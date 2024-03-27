package com.zerosword.feature_main.ui

import android.util.Log
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.zerosword.domain.model.PhotoModel
import com.zerosword.feature_main.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = searchText,
        onValueChange = { newText ->
            searchText = newText
        },
        placeholder = { Text("검색") },
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "검색 아이콘")
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide() // 키보드 숨기기
            onSearch(searchText) // 검색 실행
        }),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    )
}

@Composable
fun ImageGridList(modifier: Modifier, spanCount: Int, imageList: LazyPagingItems<PhotoModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(spanCount), // 3열 그리드
    ) {
        items(imageList.itemCount) { index ->
            val item = imageList[index]
            val url: String? = item?.thumb
            Box(Modifier.fillMaxSize()) {
                AsyncImage(
                    model = url,
                    contentDescription = null, // 접근성을 위한 설명 필요
                    modifier = modifier
                        .padding(2.dp)
                        .aspectRatio(1f)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun ImageView(photoModel: LazyPagingItems<PhotoModel>) {
    // spanCount의 변경을 저장하기 위한 state
    var spanCount by remember { mutableStateOf(3) }
    // spanCount가 변경되었는지를 추적하기 위한 상태
    val spanCountState = remember { mutableStateOf(spanCount) }
    var isGestureDetectionEnabled by remember { mutableStateOf(true) }

    val modifier = Modifier
        .pointerInput(Unit) {
            detectTransformGestures { _, _, zoom, _ ->
                if (isGestureDetectionEnabled) {
                    Log.d("Zoom", "change zoom $zoom")
                    if (zoom > 1f && spanCountState.value > 3) {
                        spanCountState.value =
                            spanCountState.value - 1
                        isGestureDetectionEnabled = false
                    } else if (zoom < 1f && spanCountState.value < 9) {
                        spanCountState.value =
                            spanCountState.value + 1
                        isGestureDetectionEnabled = false
                    }
                }
            }
        }
        .fillMaxSize()

    // spanCountState의 값이 변경될 때만 spanCount를 업데이트하여 불필요한 재구성을 방지
    LaunchedEffect(spanCountState.value) {
        spanCount = spanCountState.value
        delay(100)
        isGestureDetectionEnabled = true
    }

    photoModel.let {
        Box(modifier = modifier) {
            ImageGridList(modifier = Modifier, spanCount = spanCount, imageList = it)
        }
    }
}

@Composable
fun MainView() {
    val viewModel: MainViewModel = hiltViewModel()
    val photoModel = viewModel.photos.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {

        Column {
            SearchBar() {
                viewModel.updateSearchQuery(it)
            }

            photoModel.let { list ->
                ImageView(photoModel = list)
            }

        }

    }

    // 핀치 줌 제스처를 통해 spanCount를 변경하는 로직

}
