package ru.itis.karakurik.androidLab2.presentation

import Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherListUseCase
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherListUseCase: GetWeatherListUseCase
) : ViewModel() {

    private val _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather

    private val _weatherList: MutableLiveData<Result<List<Weather>>> = MutableLiveData()
    val weatherList: LiveData<Result<List<Weather>>> = _weatherList

    private val _cityId: MutableLiveData<Event<Result<Int>>> = MutableLiveData()
    val cityId: LiveData<Event<Result<Int>>> = _cityId

    fun onGetWeather(cityId : Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                getWeatherUseCase(cityId)
            }.onSuccess {
                _weather.value = Result.success(it)
            }.onFailure {
                _weather.value = Result.failure(it)
            }
        }
    }

    fun onGetWeatherList(lat: Double, lon: Double, cnt: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                getWeatherListUseCase(lat, lon, cnt)
            }.onSuccess {
                _weatherList.value = Result.success(it)
            }.onFailure {
                _weatherList.value = Result.failure(it)
            }
        }
    }

    fun onGetCityId(cityName: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getWeatherUseCase(cityName).id
            }.onSuccess {
                _cityId.value = Event(Result.success(it))
            }.onFailure {
                _cityId.value = Event(Result.failure(it))
            }
        }
    }
}
