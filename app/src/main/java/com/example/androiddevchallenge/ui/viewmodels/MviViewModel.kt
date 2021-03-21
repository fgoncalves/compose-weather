package com.example.androiddevchallenge.ui.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.ui.viewmodels.intents.Intent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class MviViewModel<STATE> : ViewModel(), LifecycleObserver {
    private val intentChannel = Channel<Intent>(Channel.UNLIMITED)
    protected val internalState by lazy { MutableStateFlow(initialState()) }

    val state: StateFlow<STATE>
        get() = internalState

    init {
        launchIntentHandling()
    }

    /**
     * Communicate to this viewmodel the given intent
     */
    fun dispatch(intent: Intent) {
        intentChannel.offer(intent)
    }

    /**
     * Apply the given intent to the current state and return the new one.
     *
     * This method should have no side effects. Just produce the new state
     */
    protected abstract suspend fun reduce(intent: Intent): STATE

    /**
     * Return here the initial state
     */
    protected abstract fun initialState(): STATE

    private fun launchIntentHandling() {
        intentChannel
            .consumeAsFlow()
            .onEach {
                internalState.value = reduce(it)
            }
            .launchIn(viewModelScope)
    }
}