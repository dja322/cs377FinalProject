package com.app.cs377final.data.repository

import com.app.cs377final.data.database.GameDao
import com.app.cs377final.data.database.model.Game
import com.app.cs377final.network.RetrofitClient
import com.app.cs377final.network.model.GameResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class GameRepository(private val gameDao: GameDao) {
    // Fetch a random game image from the Dog API
    fun fetchGame(searchTerm: String): Call<List<GameResponse>> {
        return RetrofitClient.apiService.searchGameDeals(searchTerm)
    }

    // Insert a game image into the favorites list in the database
    suspend fun insertFavoriteGame(game: Game) {
        gameDao.insertFavoriteGame(game)
    }

    // Get the list of favorite games from the database
    fun getFavoriteGames(): Flow<List<Game>> {
        return gameDao.getFavoriteGames()
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }
}