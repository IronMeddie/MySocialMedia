package com.ironmeddie.feature_new_post.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ironmeddie.common.TransparentHintTextField
import com.ironmeddie.common.util.compressUri
import kotlinx.coroutines.launch


@Composable
fun NewPostScreen(
    navController: NavController,
    viewModel: NewPostViewModel = hiltViewModel()
) {
    val description = viewModel.description.collectAsState().value

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val contract =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                scope.launch {
                   selectedImageUri = compressUri(uri,context)
                }
            })


    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back arrow",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(text = "New Post", style = MaterialTheme.typography.h6)
                TextButton(
                    onClick = {
                        viewModel.onEvent(
                            PostScreenEvent.PublishPost(
                                selectedImageUri!!
                            )
                        )
                        navController.navigateUp()
                    },
                    enabled = selectedImageUri != null
                ) {
                    Text(text = "Publish")
                }

            }
        }


    ) { paddings ->
        Column(modifier = Modifier.padding(paddings)) {
            TransparentHintTextField(
                text = description.text,
                hint = description.hint,
                onValueChange = { viewModel.onEvent(PostScreenEvent.UpdateDescription(it)) },
                onFocusChange = { viewModel.onEvent(PostScreenEvent.FocusChanged(it)) },
                isHintVisible = description.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    contract.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(45.dp)
            ) {
                Text(text = "Choose photo")
            }
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "selected image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        }

    }


}



