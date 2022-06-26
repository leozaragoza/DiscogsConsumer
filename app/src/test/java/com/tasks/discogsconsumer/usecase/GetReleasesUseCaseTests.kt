package com.tasks.discogsconsumer.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import com.tasks.discogsconsumer.domain.model.ArtistReleasesResponse
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.domain.usecase.GetReleasesUseCase
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
class GetReleasesUseCaseTests {
    private lateinit var getReleasesUseCase: GetReleasesUseCase

    @Mock
    private lateinit var releaseRepo: ReleasesRepo

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var releasesResponse: PagingSource<Int, Release>

    @Mock
    private lateinit var releasesResponse2: ArtistReleasesResponse

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getReleasesUseCase = GetReleasesUseCase(releaseRepo)
    }

    @Test
    fun `verify calling get releases page use case`() {
        runBlocking {
            Mockito.`when`(releaseRepo.getPagedReleases()).thenReturn(releasesResponse)
            val result = getReleasesUseCase.getPagedReleases()
            Mockito.verify(releaseRepo).getPagedReleases()
            Assert.assertEquals(result, releasesResponse)
        }
    }
}