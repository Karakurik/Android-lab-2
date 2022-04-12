package ru.itis.karakurik.androidLab2.presentation.fragments.weather

import androidx.lifecycle.*
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

//    @AssistedFactory
//    interface Factory {
//        fun create(@Assisted cityId: Int): Factory
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    companion object {
//        fun provideFactory(
//            assistedFactory: Factory,
//            cityId: Int
//        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return assistedFactory.create(cityId) as T
//            }
//        }
//    }

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

//@Module
//@InstallIn(ActivityRetainedComponent::class)
//interface AssistedInjectModule
