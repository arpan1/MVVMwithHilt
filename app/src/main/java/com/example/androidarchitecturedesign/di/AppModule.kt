package com.example.androidarchitecturedesign.di

import com.example.androidarchitecturedesign.domain.UserRepository
import com.example.androidarchitecturedesign.domain.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule{

    @Binds
    @Singleton
    abstract  fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}