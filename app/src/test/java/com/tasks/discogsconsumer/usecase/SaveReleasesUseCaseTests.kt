package com.tasks.discogsconsumer.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.domain.usecase.SaveReleasesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SaveReleasesUseCaseTests {
    private lateinit var saveReleasesUseCase: SaveReleasesUseCase

    @Mock
    private lateinit var releasesRepo: ReleasesRepo

    @Mock
    private lateinit var releases: List<Release>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        saveReleasesUseCase = SaveReleasesUseCase(releasesRepo)
    }

    @Test
    fun `verify when calling save releases then the save release method is called in the releases repo`() {
        runBlocking {
            saveReleasesUseCase.saveReleases(releases)
            Mockito.verify(releasesRepo).saveReleases(releases)
        }
    }
}