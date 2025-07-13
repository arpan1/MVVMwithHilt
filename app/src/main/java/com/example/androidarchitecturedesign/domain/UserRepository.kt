package com.example.androidarchitecturedesign.domain

import com.example.androidarchitecturedesign.data.model.UserResponse


interface UserRepository {

    suspend  fun fetchUsers() : List<UserResponse>
 //fun fetchUsers() : String

}