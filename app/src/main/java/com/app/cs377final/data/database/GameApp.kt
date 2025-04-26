package com.app.cs377final.data.database

import android.app.Application
import com.app.cs377final.data.repository.GameRepository

class GameApp : Application() {
    lateinit var repository: GameRepository
        private set

    override fun onCreate() {
        super.onCreate()

        val database = GameDatabase.getInstance(this)
        repository = GameRepository(
            gameDao = database.gameDao()
        )
    }
}