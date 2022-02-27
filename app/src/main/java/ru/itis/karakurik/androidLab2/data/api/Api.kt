package ru.itis.karakurik.androidLab2.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.karakurik.androidLab2.data.api.response.WeatherCitiesResponse
import ru.itis.karakurik.androidLab2.data.api.response.WeatherResponse

interface Api {
    @GET("weather")
    suspend fun getWeather(@Query("q") city: String): WeatherResponse

    @GET("find")
    suspend fun getWeatherCities(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int
    ) : WeatherCitiesResponse
}
