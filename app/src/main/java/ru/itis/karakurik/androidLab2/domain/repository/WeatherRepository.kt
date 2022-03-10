package ru.itis.karakurik.androidLab2.domain.repository

import ru.itis.karakurik.androidLab2.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeather(city: String): Weather
    suspend fun getWeather(id: Int): Weather
    suspend fun getWeatherList(lat: Double, lon: Double, cnt: Int): MutableList<Weather>
}
