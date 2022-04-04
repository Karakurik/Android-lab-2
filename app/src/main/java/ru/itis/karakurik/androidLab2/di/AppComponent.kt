package ru.itis.karakurik.androidLab2.di

import dagger.BindsInstance
import dagger.Component
import ru.itis.karakurik.androidLab2.WeatherApp
import ru.itis.karakurik.androidLab2.di.module.AppModule
import ru.itis.karakurik.androidLab2.di.module.NetModule
import ru.itis.karakurik.androidLab2.di.module.RepoModule
import ru.itis.karakurik.androidLab2.di.module.ViewModelModule
import ru.itis.karakurik.androidLab2.presentation.MainActivity
import ru.itis.karakurik.androidLab2.presentation.fragments.cities.SearchFragment
import ru.itis.karakurik.androidLab2.presentation.fragments.weather.DetailsFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(searchFragment: SearchFragment)
    fun inject(detailsFragment: DetailsFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: WeatherApp): Builder
    }
}
