package ru.itis.karakurik.androidLab2.data.api.mapper

import javax.inject.Inject

class WeatherIconUrlMapper @Inject constructor() {

    fun mapToLargeIcon(iconId: String): String {
        return "http://openweathermap.org/img/wn/${iconId}@2x.png"
    }

    fun mapToSmallIcon(iconId: String): String {
        return "http://openweathermap.org/img/wn/${iconId}.png"
    }
}
