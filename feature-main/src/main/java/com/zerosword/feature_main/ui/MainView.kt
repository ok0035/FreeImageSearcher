package com.zerosword.feature_main.ui

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Constraints
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
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        ),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    )
}

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    spanCount: Int,
    scrollState: ScrollState,
    content: @Composable () -> Unit
) {

    Box(modifier = modifier.verticalScroll(scrollState)) {
        Layout(
            content = content,
            modifier = modifier
        ) { measurables, constraints ->
            // 컬럼 너비 계산
            val columnWidth = constraints.maxWidth / spanCount
            val columnHeights = IntArray(spanCount) { 0 } // 각 컬럼의 높이를 저장
            val placeables = measurables.map { measurable ->
                val column = columnHeights.indices.minByOrNull { columnHeights[it] } ?: 0
                val placeable = measurable.measure(Constraints.fixedWidth(columnWidth))
                columnHeights[column] += placeable.height
                Pair(column, placeable)
            }

            val width = constraints.maxWidth
            val height = columnHeights.maxOrNull()
                ?.coerceIn(constraints.minHeight, constraints.maxHeight) ?: constraints.minHeight

            layout(width, height) {
                val columnYOffsets = IntArray(spanCount) { 0 }
                placeables.forEach { (column, placeable) ->
                    placeable.place(x = column * columnWidth, y = columnYOffsets[column])
                    columnYOffsets[column] += placeable.height
                }
            }
        }
    }
}

@Composable
fun <T> ImageGridList(
    modifier: Modifier,
    spanCount: Int,
    scrollState: ScrollState,
    imageList: List<T>,
    onBind: @Composable (item: T, modifier: Modifier) -> Unit
) {

    StaggeredVerticalGrid(
        modifier = modifier.padding(2.dp),
        scrollState = scrollState,
        spanCount = spanCount,
    ) {
        imageList.forEach { item ->
            onBind(item, modifier)
        }
    }
}

@Composable
fun ImageView(photoModel: List<PhotoModel>, pagination: () -> Unit) {
    // spanCount의 변경을 저장하기 위한 state
    val scrollState = rememberScrollState()
    var spanCount by remember { mutableStateOf(3) }
    // spanCount가 변경되었는지를 추적하기 위한 상태
    val spanCountState = remember { mutableStateOf(spanCount) }
    var isGestureDetectionEnabled by remember { mutableStateOf(true) }

    val modifier = Modifier
        .pointerInput(Unit) {
            detectTransformGestures { _, _, zoom, _ ->
                if (isGestureDetectionEnabled) {
                    Log.d("Zoom", "change zoom $zoom")
                    if (zoom > 1f && spanCountState.value > 1) {
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

    Box(modifier = modifier) {

        AnimatedContent(
            targetState = spanCount,
            transitionSpec = {
                // 애니메이션 효과를 정의합니다. fadeIn + expandVertically 등 조합 가능
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
            }, label = ""
        ) { spanCount ->
            ImageGridList(
                modifier = Modifier,
                spanCount = spanCount,
                imageList = photoModel,
                scrollState = scrollState,
            ) { item, modifier ->
                val url: String? = item.raw
                Box(
                    modifier
                        .wrapContentSize()
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = null, // 접근성을 위한 설명 필요
                        modifier = modifier
                            .padding(6.dp)
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }

    // spanCountState의 값이 변경될 때만 spanCount를 업데이트하여 불필요한 재구성을 방지
    LaunchedEffect(spanCountState.value) {
        spanCount = spanCountState.value
        delay(500)
        isGestureDetectionEnabled = true
    }

    // 스크롤 상태를 사용하여 현재 스크롤 위치 감지
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }.collect { scrollValue ->
            // 여기서 스크롤이 전체 높이의 80%에 도달했는지 계산
            // 전체 스크롤 가능한 범위: scrollState.maxValue
            if (scrollValue > scrollState.maxValue * 0.8) {
                // 스크롤이 80% 이상에 도달했을 때의 로직
                println("스크롤 80% 도달")
                pagination()
            }
        }
    }

}

@Composable
fun MainView() {
    val viewModel: MainViewModel = hiltViewModel()
    val photoModel = viewModel.currentList.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        Column {
            SearchBar() {
                viewModel.searchQuery(it)
            }

            ImageView(photoModel = photoModel.value) {
                //pagination
                viewModel.nextPage()
            }

        }

    }

}
