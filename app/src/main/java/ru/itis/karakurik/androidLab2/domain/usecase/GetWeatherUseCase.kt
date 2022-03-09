package ru.itis.karakurik.androidLab2.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    suspend operator fun invoke(city: String): Weather {
        return withContext(dispatcher) {
            weatherRepository.getWeather(city)
        }
    }

    suspend operator fun invoke(id: Int): Weather {
        return withContext(dispatcher) {
            weatherRepository.getWeather(id)
        }
    }
}
