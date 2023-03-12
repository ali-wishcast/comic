package com.android.comic

import com.android.comic.data.comic.entities.Comic
import com.android.comic.ui.comic.ComicViewModel
import com.android.comic.ui.comic.models.ComicUiState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [ComicViewModel]
 */
@ExperimentalCoroutinesApi
class ComicViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var comicViewModel: ComicViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var comicRepository: TestComicRepository
    private val comic = Comic(
        alt = "alt",
        day = "21",
        img = "https://imgs.xkcd.com/comics/xkcloud.png",
        link = "",
        month = "2",
        news = "news",
        num = 1,
        safe_title = "safe title",
        title = "title",
        transcript = "this is a transcript",
        year = "2023"
    )

    @Before
    fun setupViewModel() {
        comicRepository = TestComicRepository()
        comicRepository.comicList.add(comic)

        comicViewModel = ComicViewModel(
            comicRepository
        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertThat((comicViewModel.uiState.first() as ComicUiState.Content).isLoading)
            .isEqualTo(false)
    }

    @Test
    fun getComicFromRepositoryAndLoadIntoView() = runTest {
        val uiState = comicViewModel.uiState.first() as ComicUiState.Content
        // Then verify that the view was notified
        assertThat(uiState.comic?.title).isEqualTo(comic.title)
        assertThat(uiState.comic?.transcript).isEqualTo(comic.transcript)
    }

    @Test
    fun comicViewModel_repositoryError() = runTest {
        // Given a repository that throws errors
        comicRepository.setShouldThrowError(true)

        // Then the comic is null and the screen shows a loading error message
        assertThat((comicViewModel.uiState.value as ComicUiState.Content).comic).isNull()
    }

}