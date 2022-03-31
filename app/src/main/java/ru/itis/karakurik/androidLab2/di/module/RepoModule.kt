package ru.itis.karakurik.androidLab2.di.module

import dagger.Binds
import dagger.Module
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepository
import ru.itis.karakurik.androidLab2.domain.repository.WeatherRepositoryImpl

@Module
interface RepoModule {

    @Binds
    fun bindWeatherRepository(
        impl: WeatherRepositoryImpl,
    ): WeatherRepository
}
