package ru.itis.karakurik.androidLab2.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepository

class GetWeathersUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(lat: Double, lon: Double, cnt: Int): MutableList<Weather> {
        return withContext(dispatcher) {
            weatherRepository.getWeathers(lat, lon, cnt)
        }
    }
}
