package com.stogramm.composetest3.ui.screens.PhotoWath

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.stogramm.composetest3.ui.theme.Black
import kotlinx.coroutines.launch


const val photoViewerRoute = "Photo_viewer_route"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoWatch(viewModel : PhotoWatchViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    var scale by remember { mutableStateOf(1f) }
    var translationX by remember { mutableStateOf(0f) }
    var translationY by remember { mutableStateOf(0f) }

    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state){
            is PhotoState.Success -> {
                AsyncImage(
                    model = state.post.fileUrl, contentDescription = "Photo watch", modifier = Modifier
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { /* // NADA :) */ },
                            onDoubleClick = {
                                if (scale >= 2f) {
                                    scale = 1f
                                    translationX = 1f
                                    translationY = 1f
                                } else scale = 3f
                            },
                        )
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = translationX,
                            translationY = translationY,
                        )
                        .pointerInput(Unit) {
                            scope.launch {
                                detectTransformGestures { centroid, pan, zoom, rotation ->
                                    val maxX = (size.width * (scale - 1)) / 2
                                    val minX = -maxX
                                    val maxY = (size.height * (scale - 1)) / 2
                                    val minY = -maxY
                                    val minScale = 1f
                                    val maxScale = 3f
                                    scale = maxOf(minScale, minOf(scale * zoom, maxScale))
                                    translationX =
                                        maxOf(minX, minOf(maxX, translationX + pan.x * scale))
                                    translationY =
                                        maxOf(minY, minOf(maxY, translationY + pan.y * scale))
                                }
                            }

                        }, alignment = Alignment.Center
                )
            }
            is PhotoState.Loading ->{
                CircularProgressIndicator()
            }
            is PhotoState.Error->{
                Text(text = state.message)
            }
        }

    }


}


@Preview(showBackground = true)
@Composable
fun PhotoWatchPreview() {
    PhotoWatch()
}





