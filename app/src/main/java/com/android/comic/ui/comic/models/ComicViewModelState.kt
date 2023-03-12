package com.android.comic.ui.comic.models

import com.android.comic.data.comic.entities.Comic

data class ComicViewModelState(
    val currentComicId: Int = 1,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val comic: Comic? = null,

    val isNextComicAvailable: Boolean = true,
    val isPreviousComicAvailable: Boolean = false,

    val onPreviousButtonCLicked: () -> Unit,
    val onRandomButtonCLicked: () -> Unit,
    val onNextButtonCLicked: () -> Unit,

    ) {


    fun toUiState(): ComicUiState {
        return if (isDataSufficient()) {
            ComicUiState.Content(
                isLoading = isLoading,
                errorMessage = errorMessage,
                comic = comic,
                isNextComicAvailable = isNextComicAvailable,
                isPreviousComicAvailable = isPreviousComicAvailable,
                onPreviousButtonCLicked = onPreviousButtonCLicked,
                onRandomButtonCLicked = onRandomButtonCLicked,
                onNextButtonCLicked = onNextButtonCLicked,
            )

        } else {
            ComicUiState.Error(
                errorMessage = errorMessage,
            )

        }
    }

    private fun isDataSufficient(): Boolean {
        return errorMessage == null
    }
}