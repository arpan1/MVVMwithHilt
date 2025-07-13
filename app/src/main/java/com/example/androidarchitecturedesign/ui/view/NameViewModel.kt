package com.example.androidarchitecturedesign.ui.view

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidarchitecturedesign.data.model.UserResponse
import com.example.androidarchitecturedesign.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(private  val userRepo : UserRepository) :  ViewModel(){


    private val _state = MutableStateFlow<UiState<UserResponse>>(UiState.Idle)
    val state: StateFlow<UiState<UserResponse>> = _state.asStateFlow()

    fun getFetchUsers4(){

    }

    fun getFetchUsers8(){

    }

    fun fetchUsers(){
        viewModelScope.launch {
            _state.value = UiState.Loading

            try{
                val users = userRepo.fetchUsers()
                _state.value = UiState.Success(users)



            }

            catch (e: Exception){
            _state.value = UiState.Error(e.localizedMessage?:"Unknown Error")


            }

        }


    }


}