package com.app.cs377final.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.cs377final.data.database.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    // Abstract function to get the DAO
    abstract fun gameDao(): GameDao

    companion object {
        private var gameDatabase: GameDatabase? = null

        // Singleton pattern to ensure only one instance of the database is created
        fun getInstance(context: Context): GameDatabase {
            return gameDatabase ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).build()
                gameDatabase = instance
                instance
            }
        }
    }
}