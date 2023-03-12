package com.android.comic.ui.comic.models

import com.android.comic.data.comic.entities.Comic

sealed interface ComicUiState {
    data class Error(
        val errorMessage: String?,
    ) : ComicUiState

    data class Content(
        val isLoading: Boolean,
        val errorMessage: String?,
        val comic: Comic?,
        val isNextComicAvailable: Boolean,
        val isPreviousComicAvailable: Boolean,

        val onPreviousButtonCLicked: () -> Unit,
        val onRandomButtonCLicked: () -> Unit,
        val onNextButtonCLicked: () -> Unit,
    ) : ComicUiState
}