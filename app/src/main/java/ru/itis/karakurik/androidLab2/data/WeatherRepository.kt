package ru.itis.karakurik.androidLab2.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.karakurik.androidLab2.BuildConfig
import ru.itis.karakurik.androidLab2.data.api.Api
import ru.itis.karakurik.androidLab2.data.api.Interceptors.ApiKeyInterceptor
import ru.itis.karakurik.androidLab2.data.api.Interceptors.LangInterceptor
import ru.itis.karakurik.androidLab2.data.api.Interceptors.UnitsInterceptor
import ru.itis.karakurik.androidLab2.data.api.response.WeatherCitiesResponse
import ru.itis.karakurik.androidLab2.data.api.response.WeatherResponse

private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

class WeatherRepository {
    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(UnitsInterceptor())
            .addInterceptor(LangInterceptor())
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

    private val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    suspend fun getWeather(city: String): WeatherResponse {
        return api.getWeather(city)
    }

    suspend fun getWeatherCities(lat: Double, lon: Double, cnt: Int): WeatherCitiesResponse {
        return api.getWeatherCities(lat, lon, cnt)
    }
}
