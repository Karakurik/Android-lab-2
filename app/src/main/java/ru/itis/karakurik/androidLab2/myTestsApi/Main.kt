package ru.itis.karakurik.androidLab2

import kotlinx.coroutines.runBlocking
import ru.itis.karakurik.androidLab2.data.WeatherRepository
import ru.itis.karakurik.androidLab2.models.convertors.WindDegConvertor

private const val KAZAN_LON = 49.1221
private const val KAZAN_LAT = 55.7887

fun main() {
    val a = WindDegConvertor.convertWindDeg(350)

    val repository = WeatherRepository()

    runBlocking {
        val weatherResponse = repository.getWeather("Kazan")
        val lat = weatherResponse.coord.lat
        val lon = weatherResponse.coord.lon

        val weatherCitiesResponse = repository.getWeatherCities(lat, lon, 10)
        for (wR in weatherCitiesResponse.list) {
            println(wR.name + " " + wR.weather[0].description)
        }
    }
}
