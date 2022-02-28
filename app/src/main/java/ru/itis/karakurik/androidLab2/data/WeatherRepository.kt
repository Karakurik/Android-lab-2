package ru.itis.karakurik.androidLab2.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.karakurik.androidLab2.BuildConfig
import ru.itis.karakurik.androidLab2.data.api.Api
import ru.itis.karakurik.androidLab2.data.api.interceptors.ApiKeyInterceptor
import ru.itis.karakurik.androidLab2.data.api.interceptors.LangInterceptor
import ru.itis.karakurik.androidLab2.data.api.interceptors.UnitsInterceptor
import ru.itis.karakurik.androidLab2.data.api.response.citiesResponse.City
import ru.itis.karakurik.androidLab2.data.api.response.weatherResponse.WeatherResponse
import ru.itis.karakurik.androidLab2.models.CityWeather
import ru.itis.karakurik.androidLab2.models.convertors.WindDegConvertor

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

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

    suspend fun getWeather(city: String): CityWeather {
        return getCityWeather(api.getWeather(city))
    }

    private fun getCityWeather(weatherResponse: WeatherResponse) : CityWeather {
        weatherResponse.run {
            return CityWeather(
                id,
                name,
                coord.lat,
                coord.lon,
                main.temp,
                main.humidity,
                WindDegConvertor.convertWindDeg(wind.deg)
            )
        }
    }

    private fun getCityWeather(city: City) : CityWeather {
        city.run {
            return CityWeather(
                id,
                name,
                coord.lat,
                coord.lon,
                main.temp,
                main.humidity,
                WindDegConvertor.convertWindDeg(wind.deg)
            )
        }
    }

    suspend fun getCities(lat: Double, lon: Double, cnt: Int): MutableList<CityWeather> {
        val citiesResponse = api.getWeatherCities(lat, lon, cnt)
        val list = ArrayList<CityWeather>(cnt)
        for(i in 0 until cnt) {
            list.add(getCityWeather(citiesResponse.list[i]))
        }
        return list
    }
}
