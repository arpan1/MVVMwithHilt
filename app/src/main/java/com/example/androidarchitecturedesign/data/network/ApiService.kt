package com.example.androidarchitecturedesign.data.network

import com.example.androidarchitecturedesign.data.model.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}