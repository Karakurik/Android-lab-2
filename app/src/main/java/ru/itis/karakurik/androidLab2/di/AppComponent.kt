package ru.itis.karakurik.androidLab2.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.itis.karakurik.androidLab2.App
import ru.itis.karakurik.androidLab2.di.module.AppModule
import ru.itis.karakurik.androidLab2.di.module.NetModule
import ru.itis.karakurik.androidLab2.di.module.RepoModule
import ru.itis.karakurik.androidLab2.di.module.ViewModelModule
import ru.itis.karakurik.androidLab2.presentation.activities.MainActivity
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

//        @BindsInstance
//        fun context(context: Context): Builder

        @BindsInstance
        fun application(application: App): Builder
    }
}
