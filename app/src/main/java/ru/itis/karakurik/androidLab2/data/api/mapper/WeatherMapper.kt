package ru.itis.karakurik.androidLab2.data.api.mapper

import ru.itis.karakurik.androidLab2.data.api.response.citiesResponse.City
import ru.itis.karakurik.androidLab2.data.api.response.weatherResponse.WeatherResponse
import ru.itis.karakurik.androidLab2.domain.entity.Weather

class WeatherMapper(
    private val windDegMapper: WindDegMapper,
    private val weatherIconUrlMapper: WeatherIconUrlMapper
) {
    fun map(weatherResponse: WeatherResponse) : Weather = Weather(
        id = weatherResponse.id,
        name = weatherResponse.name,
        lat = weatherResponse.coord.lat,
        lon = weatherResponse.coord.lon,
        temp = weatherResponse.main.temp,
        tempMin = weatherResponse.main.tempMin,
        tempMax = weatherResponse.main.tempMax,
        humidity = weatherResponse.main.humidity,
        windDeg = windDegMapper.map(weatherResponse.wind.deg),
        windSpeed = weatherResponse.wind.speed,
        pressure = weatherResponse.main.pressure,
        iconUrl = weatherIconUrlMapper.mapToLargeIcon(weatherResponse.weather[0].icon)
    )

    fun map(city: City) : Weather = Weather(
        id = city.id,
        name = city.name,
        lat = city.coord.lat,
        lon = city.coord.lon,
        temp = city.main.temp,
        tempMin = city.main.tempMin,
        tempMax = city.main.tempMax,
        humidity = city.main.humidity,
        windDeg = windDegMapper.map(city.wind.deg),
        windSpeed = city.wind.speed,
        pressure = city.main.pressure,
        iconUrl = weatherIconUrlMapper.mapToLargeIcon(city.weather[0].icon)
    )
}
