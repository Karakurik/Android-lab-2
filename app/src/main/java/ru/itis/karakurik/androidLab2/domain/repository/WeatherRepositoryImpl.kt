package ru.itis.karakurik.androidLab2.domain.repository

import ru.itis.karakurik.androidLab2.BuildConfig
import ru.itis.karakurik.androidLab2.data.api.Api
import ru.itis.karakurik.androidLab2.data.api.mapper.WeatherMapper
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api,
    private val weatherMapper: WeatherMapper
) : WeatherRepository {

    override suspend fun getWeather(city: String): Weather {
        return weatherMapper.map(api.getWeather(city))
    }

    override suspend fun getWeather(id: Int): Weather {
        return weatherMapper.map(api.getWeather(id))
    }

    override suspend fun getWeatherList(lat: Double, lon: Double, cnt: Int): MutableList<Weather> {
        val citiesResponse = api.getWeathers(lat, lon, cnt)
        val list = ArrayList<Weather>(cnt)
        for (city in citiesResponse.list) {
            list.add(weatherMapper.map(city))
        }
        return list
    }
}
