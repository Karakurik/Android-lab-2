package ru.itis.karakurik.androidLab2.presentation.fragments.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {
    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> get() = _weather

    fun onGetWeather(cityId: Int) {
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
}
