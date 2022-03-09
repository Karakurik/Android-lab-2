package ru.itis.karakurik.androidLab2.di.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val QUERY_LANG = "lang"
private const val LANG = "RU"

class LangInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_LANG, LANG)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
