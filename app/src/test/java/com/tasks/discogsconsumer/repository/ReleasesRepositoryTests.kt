package com.tasks.discogsconsumer.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tasks.discogsconsumer.db.dao.ReleaseDao
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import com.tasks.discogsconsumer.db.repo.ReleasesRepoImpl
import com.tasks.discogsconsumer.domain.model.ArtistReleasesResponse
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.network.api.ReleasesService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class ReleasesRepositoryTests {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var releasesService: ReleasesService

    @Mock
    private lateinit var releasesDao: ReleaseDao

    private lateinit var releasesRepo: ReleasesRepo

    @Mock
    private lateinit var release: Release

    @Mock
    private lateinit var release2: Release

    @Mock
    private lateinit var releasesResponse: ArtistReleasesResponse

    @Mock
    private lateinit var releasesResponse2: ArtistReleasesResponse

    @Mock
    private lateinit var releases: List<Release>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        releasesRepo = ReleasesRepoImpl(releasesService = releasesService, releasesDao = releasesDao)
    }

    @Test
    fun `verify calling releases repo with id returns the right release`() {
        runBlocking {
            `when`(releasesDao.getRelease(1)).thenReturn(release)
            val result = releasesRepo.getRelease(1)
            verify(releasesDao).getRelease(1)
            Assert.assertEquals(result, release)
        }
    }

    @Test
    fun `verify calling releases repo with different ids return different releases`() {
        runBlocking {
            `when`(releasesDao.getRelease(1)).thenReturn(release)
            `when`(releasesDao.getRelease(2)).thenReturn(release2)
            val result = releasesRepo.getRelease(1)
            val result2 = releasesRepo.getRelease(2)
            verify(releasesDao).getRelease(1)
            verify(releasesDao).getRelease(2)
            Assert.assertNotEquals(result, release2)
            Assert.assertNotEquals(result2, release)
            Assert.assertEquals(result, release)
            Assert.assertEquals(result2, release2)
        }
    }

    @Test
    fun `verify calling artist release list`() {
        runBlocking {
            `when`(releasesService.fetchReleases(1, 1, 75)).thenReturn(releasesResponse)
            val result = releasesRepo.fetchReleases(1, 1)
            verify(releasesService).fetchReleases(1, 1, 75)
            Assert.assertEquals(result, releasesResponse)
        }
    }

    @Test
    fun `verify calling releases repo page with different pages return different results`() {
        runBlocking {
            `when`(releasesService.fetchReleases(1, 1, 75)).thenReturn(releasesResponse)
            `when`(releasesService.fetchReleases(1,2, 75)).thenReturn(releasesResponse2)
            val result = releasesRepo.fetchReleases(1, 1)
            val result2 = releasesRepo.fetchReleases(1, 2)
            verify(releasesService).fetchReleases(1, 1, 75)
            verify(releasesService).fetchReleases(1, 2, 75)
            Assert.assertNotEquals(result, releasesResponse2)
            Assert.assertNotEquals(result2, releasesResponse)
            Assert.assertEquals(result, releasesResponse)
            Assert.assertEquals(result2, releasesResponse2)
        }
    }

    @Test
    fun `verify releases dao call clean when release repo calls clean`() {
        runBlocking {
            releasesRepo.clearAll()
            verify(releasesDao).clearAll()
        }
    }

    @Test
    fun `verify releases dao call save when release repo calls save`() {
        runBlocking {
            releasesRepo.saveReleases(releases)
            verify(releasesDao).insertAll(releases)
        }
    }

    @Test
    fun `verify releases dao call getPagedReleases when release repo calls paged releases`() {
        runBlocking {
            releasesRepo.getPagedReleases()
            verify(releasesDao).getPagedReleases()
        }
    }
}