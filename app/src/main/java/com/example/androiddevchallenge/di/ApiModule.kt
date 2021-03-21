package com.example.androiddevchallenge.di

import com.example.androiddevchallenge.BuildConfig
import com.example.androiddevchallenge.commons.BASE_URL
import com.example.androiddevchallenge.data.api.WeatherApi
import com.example.androiddevchallenge.data.api.interceptors.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {
    companion object {
        @Provides
        @Reusable
        fun providesOkHttpClient(
            apiKeyInterceptor: ApiKeyInterceptor,
        ) =
            OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .apply {
                    if (BuildConfig.DEBUG)
                        addInterceptor(
                            HttpLoggingInterceptor()
                                .apply {
                                    setLevel(HttpLoggingInterceptor.Level.BODY)
                                }
                        )
                }
                .build()

        @Provides
        @Reusable
        fun providesRetrofit(
            okHttpClient: OkHttpClient,
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        @Provides
        @Singleton
        fun providesWeatherApi(
            retrofit: Retrofit,
        ) = retrofit.create<WeatherApi>()
    }
}