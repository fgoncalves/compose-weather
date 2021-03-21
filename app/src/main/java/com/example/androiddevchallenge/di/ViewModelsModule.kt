package com.example.androiddevchallenge.di

import com.example.androiddevchallenge.ui.state.AppState
import com.example.androiddevchallenge.ui.viewmodels.MainViewModel
import com.example.androiddevchallenge.ui.viewmodels.MviViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ViewModelsModule {
    @Binds
    @Reusable
    fun providesMainViewModel(viewModel: MainViewModel): MviViewModel<AppState>
}