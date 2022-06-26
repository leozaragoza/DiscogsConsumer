package com.tasks.discogsconsumer.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import com.tasks.discogsconsumer.domain.usecase.CleanReleasesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class CleanReleasesUseCaseTests {
    private lateinit var cleanReleasesUseCase: CleanReleasesUseCase

    @Mock
    private lateinit var releasesRepo: ReleasesRepo

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        cleanReleasesUseCase = CleanReleasesUseCase(releasesRepo)
    }

    @Test
    fun `verify when calling clean releases then the clean release method is called in the releases repo`() {
        runBlocking {
            cleanReleasesUseCase.cleanReleases()
            Mockito.verify(releasesRepo).clearAll()
        }
    }
}