package ru.itis.karakurik.androidLab2.di.annotation

import dagger.MapKey
import okhttp3.Interceptor
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class InterceptorKey(val value: KClass<out Interceptor>)
