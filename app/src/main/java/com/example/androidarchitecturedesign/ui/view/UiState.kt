package com.example.androidarchitecturedesign.ui.view

sealed class UiState<out T> {
    object Idle : UiState<Nothing>() // initial state
    object Loading: UiState<Nothing>()
    data class Success<T>(val data : T): UiState<T>()
    data class Error(val error:String): UiState<Nothing>()


}