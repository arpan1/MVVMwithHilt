package com.example.androidarchitecturedesign.di

import com.example.androidarchitecturedesign.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


    @Provides
@Singleton
fun provideBasUrl() = "https://jsonplaceholder.typicode.com/"


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor:  HttpLoggingInterceptor): OkHttpClient=
        OkHttpClient.Builder().addInterceptor (loggingInterceptor).build()


    @Provides
    @Singleton
    fun provideRetrofit(
        baseurl:String,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder().baseUrl(baseurl).client(client).addConverterFactory(
        GsonConverterFactory.create()).build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService{

        return retrofit.create(ApiService::class.java)
    }
}

