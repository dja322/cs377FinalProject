package com.app.cs377final.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.cs377final.data.database.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert
    suspend fun insertFavoriteGame( game: Game)

    @Query("SELECT * FROM Games")
    fun getFavoriteGames(): Flow<List<Game>>

    @Delete
    suspend fun deleteGame(game: Game) // New method for deletion
}