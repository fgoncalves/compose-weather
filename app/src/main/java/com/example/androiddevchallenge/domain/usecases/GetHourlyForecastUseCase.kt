package com.example.androiddevchallenge.domain.usecases

import com.example.androiddevchallenge.data.api.WeatherApi
import com.example.androiddevchallenge.domain.entities.Result
import com.example.androiddevchallenge.domain.toDomainEntity
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

interface GetHourlyForecastUseCase {
    suspend fun get(lat: Float, lon: Float): Result
}

class GetHourlyForecastUseCaseImpl @Inject constructor(
    private val weatherApi: WeatherApi,
) : GetHourlyForecastUseCase {
    override suspend fun get(lat: Float, lon: Float) =
        try {
            Result.Success(weatherApi.get(lat, lon).toDomainEntity())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Result.Failure.NoNetwork(throwable)
                is HttpException -> Result.Failure.RemoteError(throwable)
                else -> Result.Failure.UnknownError(throwable)
            }
        }
}