package ru.itis.karakurik.androidLab2.domain.usecase

import io.mockk.*
import ru.itis.karakurik.androidLab2.utils.MainCoroutineRule
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepository

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
internal class GetWeatherListUseCaseTest {
    @MockK
    lateinit var repository: WeatherRepository

    @get:Rule
    val coroutineRule: MainCoroutineRule = MainCoroutineRule()

    private lateinit var useCase: GetWeatherListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = spyk(GetWeatherListUseCase(repository, coroutineRule.testDispatcher))
    }

    @Test
    @DisplayName("Successful getting weather list")
    operator fun invoke() = runBlocking {
        val expectedList = arrayListOf<Weather>(
            mockk { every { id } returns 1 },
            mockk { every { id } returns 2 },
        )

        coEvery { repository.getWeatherList(any(), any(), any()) } returns expectedList

        val result = useCase(1.0, 1.0, 5)

        assertEquals(expectedList, result)
    }
}