package com.example.gitscope.domain.usecase

import com.example.gitscope.data.model.Repository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class CalculateTotalForkUseCaseTest {
    private val calculateTotalForkUseCase = CalculateTotalForkUseCase()

    @Test
    fun `invoke with empty list returns 0`() {
        val result = calculateTotalForkUseCase(emptyList())

        assertEquals(0, result)
    }

    @Test
    fun `invoke with non-empty list returns correct total`() {
        val repositories = listOf(
            Repository("repo1", "desc1", "2025-09-20T00:00:00Z", 100, 100),
            Repository("repo2", "desc2", "2025-09-20T00:00:00Z", 200, 200),
            Repository("repo3", "desc3", "2025-09-20T00:00:00Z", 300, 300)
        )

        assertEquals(600, calculateTotalForkUseCase(repositories))
    }

    @Test
    fun `invoke with single repository returns correct total`() {
        val repositories = listOf(
            Repository("repo1", "desc1", "2025-09-20T00:00:00Z", 100, 100)
        )

        assertEquals(100, calculateTotalForkUseCase(repositories))
    }

}