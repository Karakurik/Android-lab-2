package ru.itis.karakurik.androidLab2.di.module

import dagger.Binds
import dagger.Module
import ru.itis.karakurik.androidLab2.data.api.repository.WeatherRepositoryImpl
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepository

@Module
interface RepoModule {

    @Binds
    fun bindWeatherRepository(
        impl: WeatherRepositoryImpl,
    ): WeatherRepository
}
