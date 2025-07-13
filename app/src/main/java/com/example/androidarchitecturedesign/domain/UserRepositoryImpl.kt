package com.example.androidarchitecturedesign.domain

import com.example.androidarchitecturedesign.data.model.UserResponse
import com.example.androidarchitecturedesign.data.network.ApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {

    override suspend fun fetchUsers(): List<UserResponse>{

     return apiService.getUsers()

        //return  "Checking Hilt"

    }


}