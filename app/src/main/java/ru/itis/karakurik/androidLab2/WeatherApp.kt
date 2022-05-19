package ru.itis.karakurik.androidLab2

import android.app.Application
import ru.itis.karakurik.androidLab2.di.AppComponent
import ru.itis.karakurik.androidLab2.di.DaggerAppComponent

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
//            .context(context = this)
            .application(this)
            .build()
    }
}
