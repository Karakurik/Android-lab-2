package ru.itis.karakurik.androidLab2.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherListUseCase
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import ru.itis.karakurik.androidLab2.presentation.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherListUseCase: GetWeatherListUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(
                    getWeatherUseCase,
                    getWeatherListUseCase
                ) as? T ?: throw IllegalArgumentException("Unknown ViewModel class")

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
