package ru.itis.karakurik.androidLab2

import android.app.Application
import ru.itis.karakurik.androidLab2.di.AppComponent
import ru.itis.karakurik.androidLab2.di.DaggerAppComponent
import ru.itis.karakurik.androidLab2.di.module.AppModule
import ru.itis.karakurik.androidLab2.di.module.NetModule

class App : Application() {

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
