package com.app.cs377final.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cs377final.R
import com.app.cs377final.data.database.GameDatabase
import com.app.cs377final.data.database.model.Game
import com.app.cs377final.data.repository.GameRepository
import com.app.cs377final.ui.adapter.FavoritesAdapter
import com.app.cs377final.ui.adapter.GamesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FavoritesActivity : AppCompatActivity() {

    var gameList : List<Game>? = null

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.favorite_main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val gameDatabase = GameDatabase.getInstance(this)
        val repository = GameRepository(gameDatabase.gameDao())

        val homeButton = findViewById<Button>(R.id.homeButton)

        homeButton.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val favRecyclerView = findViewById<RecyclerView>(R.id.favorite_recycler_view)
        favRecyclerView.layoutManager = LinearLayoutManager(this)

//        val favRecyclerView = findViewById<RecyclerView>(R.id.favorite_recycler_view)

        runDatabasequery(repository)
//        val gameAdapter = FavoritesAdapter(games, repository)
//        favRecyclerView.adapter = gameAdapter


    }

    fun runDatabasequery(repository: GameRepository) {
        CoroutineScope(Dispatchers.Main).launch { // Launch on the Main dispatcher
            val games = withContext(Dispatchers.IO) { // Switch to IO dispatcher
                repository.getFavoriteGames() // This will run on the IO thread
            }

            Log.d("Games", games.toString())

            val favRecyclerView = findViewById<RecyclerView>(R.id.favorite_recycler_view)
            val gameAdapter = FavoritesAdapter(games, repository)
            favRecyclerView.adapter = gameAdapter // Update the UI on the Main thread
        }
    }

//    fun runDatabasequery(repository: GameRepository)
//    {
//        CoroutineScope(Dispatchers.IO).launch {
//            val games = repository.getFavoriteGames()
//            val favRecyclerView = findViewById<RecyclerView>(R.id.favorite_recycler_view)
//            val gameAdapter = FavoritesAdapter(games, repository)
//            favRecyclerView.adapter = gameAdapter
//        }
//    }

//    fun updateUI(games: List<Game>, repository: GameRepository)
//    {
//        CoroutineScope(Dispatchers.Main).launch {
//            val favRecyclerView = findViewById<RecyclerView>(R.id.favorite_recycler_view)
//            val gameAdapter = FavoritesAdapter(games, repository)
//            favRecyclerView.adapter = gameAdapter
//        }
//    }
}