package com.app.cs377final.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.cs377final.R
import com.app.cs377final.data.database.GameDatabase
import com.app.cs377final.data.repository.GameRepository
import com.app.cs377final.network.RetrofitClient
import com.app.cs377final.network.model.GameResponse
import com.app.cs377final.ui.adapter.GamesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var games : List<GameResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //initialize database and repository
        val gameDatabase = GameDatabase.getInstance(this)
        val repository = GameRepository(gameDatabase.gameDao())

        //initialize search button and edit text
        val searchButton = findViewById<Button>(R.id.searchButton)

        val searchEditText = findViewById<EditText>(R.id.searchEditText)

        val searchRecyclerView = findViewById<RecyclerView>(R.id.searchItemsRecyclerView)

        //set up recycler view layout manager
        searchRecyclerView.layoutManager = LinearLayoutManager(this)

        searchButton.setOnClickListener {

            //get search term from edit text
            val searchTerm = searchEditText.text.toString()
            //check if search term is empty
            if (searchTerm.isNotEmpty()) {
                //if not empty, run fetchGameDeals function
                fetchGameDeals(searchTerm)

                //set up recyclerview adapter
                val gameAdapter = GamesAdapter(games, repository)

                searchRecyclerView.adapter = gameAdapter
            }

        }
    }

    //runs retrofit client to get game deals and then sets the global varaible "games" to the new list of GameResponse objects
    private fun fetchGameDeals(title: String) {
        // Use Retrofit to make the API call
        val call: Call<List<GameResponse>> = RetrofitClient.apiService.searchGameDeals(title) // Replace "1" with the store ID you want to search for
        //parse call object into response objects
        call.enqueue(object : Callback<List<GameResponse>> {
            //onResponse function to handle successful response
            override fun onResponse(call: Call<List<GameResponse>>, response: Response<List<GameResponse>>) {
                if (response.isSuccessful) {
                    //create list of responses
                    val gameResponses: List<GameResponse>? = response.body()
                    if (gameResponses != null) {
                        //update global games list
                        games = gameResponses
                    }
                } else {
                    // Handle error
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<GameResponse>>, t: Throwable) {
                // Handle failure
                println("Failure: ${t.message}")
            }
        })
    }

}