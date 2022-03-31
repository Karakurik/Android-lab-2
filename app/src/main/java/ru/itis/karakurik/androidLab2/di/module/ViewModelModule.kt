package ru.itis.karakurik.androidLab2.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.karakurik.androidLab2.di.annotation.ViewModelKey
import ru.itis.karakurik.androidLab2.presentation.MainViewModel
import ru.itis.karakurik.androidLab2.presentation.utils.AppViewModelFactory

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        viewModel: MainViewModel
    ): ViewModel
}
