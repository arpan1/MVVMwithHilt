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
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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

//    @Provides
//    @Singleton
//    fun provideOkHttpClient(loggingInterceptor:  HttpLoggingInterceptor): OkHttpClient=
//        OkHttpClient.Builder().addInterceptor (loggingInterceptor).build()
//
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor:  HttpLoggingInterceptor): OkHttpClient = getUnsafeOkHttpClient(loggingInterceptor)


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
    fun getUnsafeOkHttpClient(loggingInterceptor:  HttpLoggingInterceptor): OkHttpClient {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            // Create an OkHttpClient that uses the all-trusting trust manager
            OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true  } // Bypass hostname verification
                .addInterceptor(loggingInterceptor)
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

