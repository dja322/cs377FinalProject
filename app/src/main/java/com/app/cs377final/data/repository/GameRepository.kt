package com.app.cs377final.data.repository

import com.app.cs377final.data.database.GameDao
import com.app.cs377final.data.database.model.Game
import com.app.cs377final.network.RetrofitClient
import com.app.cs377final.network.model.GameResponse
import com.app.cs377final.network.model.Store
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class GameRepository(private val gameDao: GameDao) {
    private val apiService = RetrofitClient.apiService
    private var storeMap: Map<String, String>? = null

    // Fetch a random game image from the Dog API
    fun fetchGame(searchTerm: String): Call<List<GameResponse>> {
        return RetrofitClient.apiService.searchGameDeals(searchTerm)
    }

    // Insert a game image into the favorites list in the database
    suspend fun insertFavoriteGame(game: Game) {
        gameDao.insertFavoriteGame(game)
    }

    // Get the list of favorite games from the database
    fun getFavoriteGames(): List<Game> {
        return gameDao.getFavoriteGames()
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    // Function to get store name from ID
    fun fetchStores(callback: (Map<String, String>) -> Unit) {
        if (storeMap != null) {
            callback(storeMap!!)
            return
        }

        apiService.getStores().enqueue(object : retrofit2.Callback<List<Store>> {
            override fun onResponse(
                call: Call<List<Store>>,
                response: retrofit2.Response<List<Store>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    storeMap = response.body()!!.associateBy({ it.storeID }, { it.storeName })
                    callback(storeMap!!)
                } else {
                    callback(emptyMap())
                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                callback(emptyMap())
            }
        })
    }
}