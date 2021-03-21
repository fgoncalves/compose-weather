package com.example.androiddevchallenge.di

import com.example.androiddevchallenge.domain.usecases.GetHourlyForecastUseCase
import com.example.androiddevchallenge.domain.usecases.GetHourlyForecastUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    @Reusable
    fun providesGetHourlyForecastUseCase(useCase: GetHourlyForecastUseCaseImpl): GetHourlyForecastUseCase
}