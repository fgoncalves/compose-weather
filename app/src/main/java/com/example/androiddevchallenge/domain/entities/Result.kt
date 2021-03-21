package com.example.androiddevchallenge.domain.entities

sealed class Result {
    data class Success<T>(
        val value: T,
    ) : Result()

    sealed class Failure : Result() {
        abstract val throwable: Throwable?

        data class NoNetwork(override val throwable: Throwable?) : Failure()
        data class RemoteError(override val throwable: Throwable?) : Failure()
        data class UnknownError(override val throwable: Throwable?) : Failure()
    }
}