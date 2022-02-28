package ru.itis.karakurik.androidLab2.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val QUERY_UNITS = "units"
private const val UNITS = "metric"

class UnitsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_UNITS, UNITS)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
