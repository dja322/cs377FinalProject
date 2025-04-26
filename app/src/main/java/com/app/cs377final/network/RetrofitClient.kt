package com.app.cs377final.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.cheapshark.com/api/1.0/"

    private val client = OkHttpClient.Builder()
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: GameDBApiService by lazy {
        retrofit.create(GameDBApiService::class.java)
    }
}