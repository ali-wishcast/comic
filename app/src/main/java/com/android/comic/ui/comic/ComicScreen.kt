package com.android.comic.ui.comic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberImagePainter
import com.android.comic.data.comic.entities.Comic
import com.android.comic.ui.comic.models.ComicUiState
import com.android.comic.ui.components.ZoomableImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@OptIn(ExperimentalLifecycleComposeApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun ComicScreen(
    comicViewModel: ComicViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        comicViewModel.getFirstComic()
    }

    when (val uiState = comicViewModel.uiState.collectAsStateWithLifecycle().value) {
        is ComicUiState.Content -> {
            ComicContent(
                uiState = uiState,
            )
        }
        is ComicUiState.Error -> {
            Text(
                modifier = Modifier.padding(4.dp),
                text = uiState.errorMessage!!,
                fontSize = 16.sp,
                color = Color.Red,
            )
        }
    }
}

@Composable
fun ComicContent(
    uiState: ComicUiState.Content,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            uiState.comic?.let {
                ComicInfo(
                    comic = it,
                    modifier = Modifier.weight(weight = 1f, fill = true),
                )
            }
            NavigationButtons(uiState)
        }
    }

}

@Composable
fun ComicInfo(
    comic: Comic,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = comic.title,
                modifier = Modifier
                    .weight(1f, true)
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.h6,
            )

            Text(
                text = comic.getPublishDate(),
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h6,
            )
        }

        val description = comic.transcript.ifEmpty {
            "Description is not available"
        }
        Text(
            text = description,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp)
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = true),
            style = MaterialTheme.typography.body1,
        )

        comic.img.let {
            val painter = rememberImagePainter(it)
            ZoomableImage(
                painter = painter,
                isRotation = false,
                isZoomable = true,
                scrollState = scrollState,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp),
            )
        }
    }
}

@Composable
fun NavigationButtons(uiState: ComicUiState.Content) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        Arrangement.SpaceAround,
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp),
            onClick = { uiState.onPreviousButtonCLicked() },
            enabled = uiState.isPreviousComicAvailable,
        ) {
            Text(text = "Previous")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = { uiState.onRandomButtonCLicked() },
        ) {
            Text(text = "Random")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp),
            onClick = { uiState.onNextButtonCLicked() },
            enabled = uiState.isNextComicAvailable,
        ) {
            Text(text = "Next")
        }
    }
}