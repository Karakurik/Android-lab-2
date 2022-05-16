package ru.itis.karakurik.androidLab2.presentation.fragments.weather

import androidx.lifecycle.*
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private var _weather: MutableStateFlow<Result<Weather>?> = MutableStateFlow(null)
    val weather: StateFlow<Result<Weather>?> = _weather.asStateFlow()

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
