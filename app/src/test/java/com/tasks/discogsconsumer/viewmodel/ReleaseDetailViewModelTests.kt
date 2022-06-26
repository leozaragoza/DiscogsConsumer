package com.tasks.discogsconsumer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.domain.usecase.GetReleaseDetailUseCase
import com.tasks.discogsconsumer.ui.viewmodel.ReleaseDetailViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class ReleaseDetailViewModelTests {
    private lateinit var releaseDetailViewModel: ReleaseDetailViewModel

    @Mock
    private lateinit var getReleaseDetailUseCase: GetReleaseDetailUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var release: Release

    @Mock
    private lateinit var release2: Release

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        releaseDetailViewModel = ReleaseDetailViewModel(getReleaseDetailUseCase)
    }

    @Test
    fun `verify calling get release detail calls the detail use case`() {
        runBlocking {
            Mockito.`when`(getReleaseDetailUseCase.getRelease(1)).thenReturn(release)
            val result = releaseDetailViewModel.getRelease(1).value
            Mockito.verify(getReleaseDetailUseCase).getRelease(1)
            Assert.assertEquals(result, release)
        }
    }

    @Test
    fun `verify calling clean data cleans the release`() {
        runBlocking {
            Mockito.`when`(getReleaseDetailUseCase.getRelease(1)).thenReturn(release)
            val result = releaseDetailViewModel.getRelease(1).value
            Mockito.verify(getReleaseDetailUseCase).getRelease(1)
            Assert.assertEquals(result, release)
            releaseDetailViewModel.cleanData()
            Assert.assertTrue(releaseDetailViewModel.release.value == null)
        }
    }

    @Test
    fun `verify calling get release detail with different ids`() {
        runBlocking {
            Mockito.`when`(getReleaseDetailUseCase.getRelease(1)).thenReturn(release)
            Mockito.`when`(getReleaseDetailUseCase.getRelease(2)).thenReturn(release2)
            val result = releaseDetailViewModel.getRelease(1).value
            releaseDetailViewModel.cleanData()
            val result2 = releaseDetailViewModel.getRelease(2).value
            Mockito.verify(getReleaseDetailUseCase).getRelease(1)
            Mockito.verify(getReleaseDetailUseCase).getRelease(2)
            Assert.assertEquals(result, release)
            Assert.assertEquals(result2, release2)
            Assert.assertNotEquals(result, release2)
            Assert.assertNotEquals(result2, release)
        }
    }
}