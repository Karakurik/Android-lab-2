package ru.itis.karakurik.androidLab2.models

data class CityWeather(
    val id: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
    val temp: Double,
    val humidity: Int,
    val windDeg: WindDeg
)
