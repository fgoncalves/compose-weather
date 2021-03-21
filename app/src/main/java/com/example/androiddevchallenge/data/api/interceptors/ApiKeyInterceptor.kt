package com.example.androiddevchallenge.data.api.interceptors

import com.example.androiddevchallenge.commons.API_KEY
import javax.inject.Inject
import okhttp3.Interceptor

class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = with(chain.request()) {
        chain.proceed(
            newBuilder()
                .url(
                    url.newBuilder()
                        .addQueryParameter("appid", API_KEY)
                        .build()
                ).build()
        )
    }
}