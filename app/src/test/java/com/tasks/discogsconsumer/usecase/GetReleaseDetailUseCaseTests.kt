package com.tasks.discogsconsumer.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.domain.usecase.GetReleaseDetailUseCase
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
class GetReleaseDetailUseCaseTests {
    private lateinit var getReleaseDetailUseCase: GetReleaseDetailUseCase

    @Mock
    private lateinit var releaseRepo: ReleasesRepo

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var release: Release

    @Mock
    private lateinit var release2: Release

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getReleaseDetailUseCase = GetReleaseDetailUseCase(releaseRepo)
    }

    @Test
    fun `verify calling get release detail use case`() {
        runBlocking {
            Mockito.`when`(releaseRepo.getRelease(1)).thenReturn(release)
            val result = getReleaseDetailUseCase.getRelease(1)
            Mockito.verify(releaseRepo).getRelease(1)
            Assert.assertEquals(result, release)
        }
    }

    @Test
    fun `verify calling get release detail with different ids return different results`() {
        runBlocking {
            Mockito.`when`(releaseRepo.getRelease(1)).thenReturn(release)
            Mockito.`when`(releaseRepo.getRelease(2)).thenReturn(release2)
            val result = getReleaseDetailUseCase.getRelease(1)
            val result2 = getReleaseDetailUseCase.getRelease(2)
            Mockito.verify(releaseRepo).getRelease(1)
            Mockito.verify(releaseRepo).getRelease(2)
            Assert.assertEquals(result, release)
            Assert.assertEquals(result2, release2)
            Assert.assertNotEquals(result, release2)
            Assert.assertNotEquals(result2, release)
        }
    }
}