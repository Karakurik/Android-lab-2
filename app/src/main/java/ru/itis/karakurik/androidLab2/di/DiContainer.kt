package ru.itis.karakurik.androidLab2.di

import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.karakurik.androidLab2.BuildConfig
import ru.itis.karakurik.androidLab2.data.api.Api
import ru.itis.karakurik.androidLab2.data.api.mapper.WeatherIconUrlMapper
import ru.itis.karakurik.androidLab2.data.api.mapper.WeatherMapper
import ru.itis.karakurik.androidLab2.data.api.mapper.WindDegMapper
import ru.itis.karakurik.androidLab2.di.interceptors.ApiKeyInterceptor
import ru.itis.karakurik.androidLab2.di.interceptors.LangInterceptor
import ru.itis.karakurik.androidLab2.di.interceptors.UnitsInterceptor
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepository
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepositoryImpl
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherListUseCase
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

object DiContainer {
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

    val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    private val windDegMapper: WindDegMapper by lazy {
        WindDegMapper()
    }

    private val weatherIconUrlMapper by lazy {
        WeatherIconUrlMapper()
    }

    private val weatherMapper: WeatherMapper by lazy {
        WeatherMapper(
            windDegMapper,
            weatherIconUrlMapper
        )
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            api,
            weatherMapper
        )
    }

    val getWeatherUseCase: GetWeatherUseCase by lazy {
        GetWeatherUseCase(weatherRepository, Dispatchers.Default)
    }

    val getWeatherListUseCase: GetWeatherListUseCase by lazy {
        GetWeatherListUseCase(weatherRepository, Dispatchers.Default)
    }
}
