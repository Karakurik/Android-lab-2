package ru.itis.karakurik.androidLab2.presentation.fragments.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherListUseCase
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherListUseCase: GetWeatherListUseCase
) : ViewModel() {

    private val _weatherList: MutableSharedFlow<Result<List<Weather>>> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 0
    )
    val weatherList: SharedFlow<Result<List<Weather>>> = _weatherList.asSharedFlow()

    private val _cityId: MutableStateFlow<Result<Int>?> = MutableStateFlow(null)
    val cityId: StateFlow<Result<Int>?> = _cityId.asStateFlow()

    fun onGetWeatherList(lat: Double, lon: Double, cnt: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                getWeatherListUseCase(lat, lon, cnt)
            }.onSuccess {
                _weatherList.emit(Result.success(it))
            }.onFailure {
                _weatherList.emit(Result.failure(it))
            }
        }
    }

    fun onGetCityId(cityName: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getWeatherUseCase(cityName).id
            }.onSuccess {
                _cityId.value = Result.success(it)
            }.onFailure {
                _cityId.value = Result.failure(it)
            }
        }
    }
}
