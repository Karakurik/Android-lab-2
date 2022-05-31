package ru.itis.karakurik.androidLab2.presentation.fragments.cities

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherListUseCase
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import ru.itis.karakurik.androidLab2.utils.getOrAwaitValue

internal class WeatherViewModelTest {

    @MockK
    lateinit var getWeatherUseCase: GetWeatherUseCase

    @MockK
    lateinit var getWeatherListUseCase: GetWeatherListUseCase

    private lateinit var viewModel: CityListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CityListViewModel(
            getWeatherUseCase,
            getWeatherListUseCase
        )
    }

    @Test
    fun getCityIdByName() = runBlocking {

        val mockCityId = mockk<Weather> { every { id } returns 1 }.id
        val expectedCityId = Result.success(mockCityId)

        coEvery { getWeatherUseCase("Kazan").id } returns mockCityId

        viewModel.onGetCityId("Kazan")

        assertEquals(expectedCityId, viewModel.cityId.getOrAwaitValue())
    }

    @Test
    fun getWeatherList() = runBlocking {

        val mockCityList = arrayListOf<Weather>(
            mockk { every { id } returns 1 },
            mockk { every { id } returns 2 },
        )

        val expectedWeather = Result.success(mockCityList)

        coEvery { getWeatherListUseCase(any(), any(), any()) } returns mockCityList

        viewModel.onGetWeatherList(1.0, 1.0, 5)

        assertEquals(expectedWeather, viewModel.weatherList.getOrAwaitValue())
    }
}