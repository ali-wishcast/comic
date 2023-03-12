package com.android.comic.ui.comic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.comic.data.comic.ComicRepository
import com.android.comic.ui.comic.models.ComicViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

const val FIRST_COMIC_ID: Int = 1
const val LAST_COMIC_ID: Int = 2745

@HiltViewModel
class ComicViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        ComicViewModelState(
            onPreviousButtonCLicked = {
                getPreviousComic()
            },
            onRandomButtonCLicked = {
                getRandomComic()
            },
            onNextButtonCLicked = {
                getNextComic()
            },
        ),
    )

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            viewModelState.value.toUiState(),
        )

    init {
        getFirstComic()
    }

    private fun getFirstComic() {
        getComicDetail(FIRST_COMIC_ID)
        updateNavigationButtonsState(FIRST_COMIC_ID)
    }

    private fun getPreviousComic() {
        val currentComicId = viewModelState.value.currentComicId
        updateNavigationButtonsState(currentComicId - 2)
        getComicDetail(currentComicId - 1)
    }

    private fun getRandomComic() {
        val randomId = Random.nextInt(FIRST_COMIC_ID, LAST_COMIC_ID)
        updateNavigationButtonsState(randomId)
        getComicDetail(randomId)
    }

    private fun getNextComic() {
        val currentComicId = viewModelState.value.currentComicId
        updateNavigationButtonsState(currentComicId + 2)
        getComicDetail(currentComicId + 1)
    }

    private fun getComicDetail(id: Int) {
        updateCurrentComicId(id)
        showLoading()
        viewModelScope.launch {
            comicRepository.getComicDetail(id).fold(
                {
                    viewModelState.update { currentViewModelState ->
                        currentViewModelState.copy(
                            currentComicId = id,
                            comic = it,
                            isLoading = false,
                        )
                    }
                },
                { throwable ->
                    //Show error
                    throwable.message?.let {
                        viewModelState.update { currentViewModelState ->
                            currentViewModelState.copy(
                                errorMessage = "",
                                isLoading = false,
                            )
                        }
                    }
                },
            )
        }
    }

    private fun updateNavigationButtonsState(currentComicId: Int) {
        viewModelState.update { currentViewModelState ->
            currentViewModelState.copy(
                isNextComicAvailable = currentComicId < LAST_COMIC_ID,
                isPreviousComicAvailable = currentComicId > FIRST_COMIC_ID,
            )
        }
    }

    private fun updateCurrentComicId(currentComicId: Int) {
        viewModelState.update { currentViewModelState ->
            currentViewModelState.copy(
                currentComicId = currentComicId,
            )
        }
    }

    private fun showLoading() {
        viewModelState.update { currentViewModelState ->
            currentViewModelState.copy(
                isLoading = true,
            )
        }
    }

}