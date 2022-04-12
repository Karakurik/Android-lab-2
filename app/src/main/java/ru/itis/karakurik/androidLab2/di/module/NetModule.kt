package ru.itis.karakurik.androidLab2.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.karakurik.androidLab2.BuildConfig
import ru.itis.karakurik.androidLab2.data.api.Api
import ru.itis.karakurik.androidLab2.di.qualifier.ApiKeyInterceptor
import ru.itis.karakurik.androidLab2.di.qualifier.LangInterceptor
import ru.itis.karakurik.androidLab2.di.qualifier.LoggingInterceptor
import ru.itis.karakurik.androidLab2.di.qualifier.UnitsInterceptor
import java.io.File
import javax.inject.Qualifier

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"
private const val QUERY_API_KEY = "appid"
private const val QUERY_LANG = "lang"
private const val LANG = "RU"
private const val QUERY_UNITS = "units"
private const val UNITS = "metric"

@InstallIn(SingletonComponent::class)
@Module
class NetModule {

    @Provides
    @ApiKeyInterceptor
    fun apiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    @Provides
    @LangInterceptor
    fun langInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_LANG, LANG)
            .build()

        chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    @Provides
    @UnitsInterceptor
    fun unitsInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_UNITS, UNITS)
            .build()

        chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
    }

    @Provides
    fun provideCacheDirectory(): File {
        return File("cache");
    }

    @Qualifier
    annotation class CacheMaxSize

    @CacheMaxSize
    @Provides
    fun provideCacheMaxSize(): Long {
        return 50 * 1024 * 1024;
    }

    @Provides
    fun provideCache(
        cacheDirectory: File,
        @CacheMaxSize cacheMaxSize: Long
    ): Cache {
        return Cache(cacheDirectory, cacheMaxSize)
    }

    @Provides
    fun provideOkhttpClient(
        cache: Cache,
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor,
        @LangInterceptor langInterceptor: Interceptor,
        @UnitsInterceptor unitsInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(langInterceptor)
            .addInterceptor(unitsInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        loggingInterceptor
                    )
                }
            }
            .build()

    @Provides
    fun provideApi(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        gsonConverter: GsonConverterFactory,
    ): Api =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .build()
            .create(Api::class.java)

    @Provides
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()
}
