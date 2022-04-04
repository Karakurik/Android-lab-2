package ru.itis.karakurik.androidLab2.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.karakurik.androidLab2.di.annotation.ViewModelKey
import ru.itis.karakurik.androidLab2.presentation.common.utils.AppViewModelFactory
import ru.itis.karakurik.androidLab2.presentation.fragments.cities.CityListViewModel
import ru.itis.karakurik.androidLab2.presentation.fragments.weather.WeatherViewModel

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CityListViewModel::class)
    fun bindCityListViewModel(
        viewModel: CityListViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    fun bindWeatherViewModel(
        viewModel: WeatherViewModel
    ): ViewModel
}
