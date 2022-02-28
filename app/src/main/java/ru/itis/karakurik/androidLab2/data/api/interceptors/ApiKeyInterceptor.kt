package ru.itis.karakurik.androidLab2.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"
private const val QUERY_API_KEY = "appid"

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
