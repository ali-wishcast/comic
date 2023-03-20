package com.android.comic

import com.android.comic.data.comic.ComicRepository
import com.android.comic.data.comic.entities.Comic
import com.android.comic.ui.comic.ComicViewModel
import com.android.comic.ui.comic.FIRST_COMIC_ID
import com.android.comic.ui.comic.LAST_COMIC_ID
import com.android.comic.ui.comic.models.ComicUiState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
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

    private val comicRepository = mockk<ComicRepository>()
    private lateinit var comicViewModel: ComicViewModel

    private val comic = Comic(
        "alt",
        "21",
        "https://imgs.xkcd.com/comics/xkcloud.png",
        "",
        "2",
        "news",
        1,
        "safe title",
        "title",
        "this is a transcript",
        "2023"
    )


    @Before
    fun setup() {
        coEvery {
            comicRepository.getComicDetail(
                range(
                    FIRST_COMIC_ID,
                    LAST_COMIC_ID
                )
            )
        } returns Result.success(comic)
        coEvery {
            comicRepository.getComicDetail(
                more(LAST_COMIC_ID)
            )
        } returns Result.failure(Throwable())
        comicViewModel = ComicViewModel(comicRepository)
    }

    @Test
    fun `Test loading state is shown before the content`() = runTest {
        assertThat((comicViewModel.uiState.first() as ComicUiState.Content).isLoading)
            .isEqualTo(true)
    }

    @Test
    fun `get comic from repository and load into view`() = runTest {
        comicViewModel.getFirstComic()
        val uiState = comicViewModel.uiState.first() as ComicUiState.Content
        // Then verify that the view was notified
        assertThat(uiState.comic).isEqualTo(comic)
    }

    @Test
    fun `get comic from repository and get error`() = runTest {
        // Given a repository that throws errors
        comicViewModel.getComicDetail(LAST_COMIC_ID + 1)

        // Then the comic is null and the screen shows a loading error message
        assertThat((comicViewModel.uiState.value as ComicUiState.Content).comic).isNull()
    }

}