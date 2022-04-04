package ru.itis.karakurik.androidLab2.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_START
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.itis.karakurik.androidLab2.WeatherApp
import javax.inject.Qualifier

@Module
class AppModule {

    @Provides
    fun provideContext(weatherApp: WeatherApp): Context = weatherApp.applicationContext

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DefaultDispatcher

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class MainDispatcher

    @Provides
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class IODispatcher

    @Provides
    fun provideSmoothScroller(
        context: Context
    ): RecyclerView.SmoothScroller = object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }

    @Provides
    fun provideFusedLocationProviderClient(
        context: Context
    ) = LocationServices.getFusedLocationProviderClient(context)
}
