package com.app.cs377final.network

import com.app.cs377final.data.database.model.Game
import com.app.cs377final.network.model.GameResponse
import com.app.cs377final.network.model.Store
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface GameDBApiService {
    @GET("deals")
    fun searchGameDeals(@Query("title") title: String): Call<List<GameResponse>>

    @GET("stores")
    fun getStores(): Call<List<Store>>
}