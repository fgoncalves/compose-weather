package com.example.androiddevchallenge.ui.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.entities.HourlyForecast
import com.example.androiddevchallenge.domain.entities.Result
import com.example.androiddevchallenge.domain.usecases.GetHourlyForecastUseCase
import com.example.androiddevchallenge.ui.state.AppState
import com.example.androiddevchallenge.ui.state.RequestState
import com.example.androiddevchallenge.ui.state.WeatherForecast
import com.example.androiddevchallenge.ui.state.toAppState
import com.example.androiddevchallenge.ui.viewmodels.intents.ErrorGettingHourlyForecastIntent
import com.example.androiddevchallenge.ui.viewmodels.intents.GetHourlyForecastIntent
import com.example.androiddevchallenge.ui.viewmodels.intents.GotHourlyForecastIntent
import com.example.androiddevchallenge.ui.viewmodels.intents.Intent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val useCase: GetHourlyForecastUseCase,
) : MviViewModel<AppState>() {

    override suspend fun reduce(intent: Intent): AppState {
        return when (intent) {
            is GetHourlyForecastIntent -> {
                val toReturn = internalState.value.copy(
                    requestState = RequestState.Loading,
                )

                when (val result = useCase.get(38.7223f, 9.1393f)) {
                    is Result.Success<*> ->
                        dispatch(GotHourlyForecastIntent(result.value as HourlyForecast))

                    is Result.Failure.RemoteError ->
                        dispatch(
                            ErrorGettingHourlyForecastIntent(
                                R.string.remote_error_title,
                                R.string.remote_error_message,
                            )
                        )

                    is Result.Failure.NoNetwork ->
                        dispatch(
                            ErrorGettingHourlyForecastIntent(
                                R.string.no_network_error_title,
                                R.string.no_network_error_message,
                            )
                        )

                    is Result.Failure.UnknownError ->
                        dispatch(
                            ErrorGettingHourlyForecastIntent(
                                R.string.unknown_error_title,
                                R.string.unknown_error_message,
                            )
                        )
                }

                toReturn
            }

            is GotHourlyForecastIntent ->
                intent.forecast.toAppState(

                    unit = internalState.value.tempUnit,
                )

            is ErrorGettingHourlyForecastIntent ->
                internalState.value.copy(
                    requestState = RequestState.Error(
                        intent.title,
                        intent.msg,
                    )
                )
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        dispatch(GetHourlyForecastIntent)
    }

    override fun initialState() =
        AppState(
            weatherForecast = WeatherForecast(
                locationName = "",
                currentDay = 0,
            ),
            requestState = RequestState.Loading,
        )
}