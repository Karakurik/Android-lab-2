package ru.itis.karakurik.androidLab2.myTestsApi

import kotlinx.coroutines.runBlocking
import ru.itis.karakurik.androidLab2.data.WeatherRepository
import ru.itis.karakurik.androidLab2.models.convertors.WindDegConvertor

private const val KAZAN_LON = 49.1221
private const val KAZAN_LAT = 55.7887

fun main() {
    val a = WindDegConvertor.convertWindDeg(350)

    val repository = WeatherRepository()

    runBlocking {
        val weather = repository.getWeather("Kazan")
        val lat = weather.lat
        val lon = weather.lon

        val weatherCities = repository.getCities(lat, lon, 10)
        for (wC in weatherCities) {
            println(wC.name + " " + wC.temp)
        }
    }
}
