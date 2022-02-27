package ru.itis.karakurik.androidLab2.models

data class CityWeather (
    val id: Long,
    val lat: Double,
    val lon: Double,
    val temp: Double,
    val humidity: Int,
    val windDeg: WindDeg
)
