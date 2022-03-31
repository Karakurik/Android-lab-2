package ru.itis.karakurik.androidLab2.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.karakurik.androidLab2.di.module.AppModule
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherListUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    @AppModule.DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    suspend operator fun invoke(lat: Double, lon: Double, cnt: Int): MutableList<Weather> {
        return withContext(dispatcher) {
            weatherRepository.getWeatherList(lat, lon, cnt)
        }
    }
}
