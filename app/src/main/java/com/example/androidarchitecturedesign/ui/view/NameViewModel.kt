package com.example.androidarchitecturedesign.ui.view

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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


     var allUsers: UserResponse = UserResponse()


    var searchQuery by mutableStateOf("")

    fun searchQueryChanged()
    {
        Log.e("Changed query--->>>",searchQuery)
        filterUser(searchQuery)

    }

    fun filterUser(searchQuery:String)
    {
        val filteredList =  allUsers.filter { it.name.contains(searchQuery, ignoreCase = true) }
        for(i in filteredList){

            Log.e("user are-->>",i.name)
        }

        val filteredUserResponse = UserResponse()
        filteredUserResponse.addAll(filteredList)

        _state.value = UiState.Success(filteredUserResponse)

    }




    fun fetchUsers(){
        viewModelScope.launch {
            _state.value = UiState.Loading

            try{
                val users = userRepo.fetchUsers()

                allUsers  = users

                _state.value = UiState.Success(users)

            }

            catch (e: Exception){
            _state.value = UiState.Error(e.localizedMessage?:"Unknown Error")


            }

        }


    }


}