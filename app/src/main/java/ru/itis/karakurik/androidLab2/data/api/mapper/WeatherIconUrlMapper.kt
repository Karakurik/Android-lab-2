package ru.itis.karakurik.androidLab2.data.api.mapper

class WeatherIconUrlMapper {
    fun mapToLargeIcon(iconId: String): String {
        return "http://openweathermap.org/img/wn/${iconId}@2x.png"
    }

    fun mapToSmallIcon(iconId: String): String {
        return "http://openweathermap.org/img/wn/${iconId}.png"
    }
}
