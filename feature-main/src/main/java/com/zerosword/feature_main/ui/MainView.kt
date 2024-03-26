package com.zerosword.feature_main.ui

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zerosword.domain.model.GetPhotoModel
import com.zerosword.feature_main.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun ImageGridList(modifier: Modifier, spanCount: Int, imageList: List<GetPhotoModel?>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(spanCount), // 3열 그리드
    ) {
        items(imageList.size) { index ->
            val item = imageList[index]
            val url: String? = item?.thumb
            AsyncImage(
                model = url,
                contentDescription = null, // 접근성을 위한 설명 필요
                modifier = modifier
                    .padding(2.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun MainView() {
    val viewModel: MainViewModel = hiltViewModel()
    val photoResponse = viewModel.photos.collectAsState()
    // spanCount의 변경을 저장하기 위한 state
    var spanCount by remember { mutableStateOf(3) }
    // spanCount가 변경되었는지를 추적하기 위한 상태
    val spanCountState = remember { mutableStateOf(spanCount) }
    var isGestureDetectionEnabled by remember { mutableStateOf(true) }

    // 핀치 줌 제스처를 통해 spanCount를 변경하는 로직
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

    photoResponse.value?.let {
        Box(modifier = modifier) {
            ImageGridList(modifier = Modifier, spanCount = spanCount, imageList = it)
        }
    }
}
