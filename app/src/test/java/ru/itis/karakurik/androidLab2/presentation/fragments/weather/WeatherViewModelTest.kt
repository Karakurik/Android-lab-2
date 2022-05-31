package ru.itis.karakurik.androidLab2.presentation.fragments.weather

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import ru.itis.karakurik.androidLab2.utils.getOrAwaitValue

internal class WeatherViewModelTest {

    @MockK
    lateinit var getWeatherUseCase: GetWeatherUseCase

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = WeatherViewModel(getWeatherUseCase)
    }

    @Test
    fun getWeatherById() = runBlocking {

        val mockWeather = mockk<Weather> { every { id } returns 1 }
        val expectedWeather = Result.success(mockWeather)

        coEvery { getWeatherUseCase(1) } returns mockWeather

        viewModel.onGetWeather(1)

        assertEquals(expectedWeather, viewModel.weather.getOrAwaitValue())
    }
}