package com.app.cs377final.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.cs377final.R
import com.app.cs377final.data.database.GameDatabase
import com.app.cs377final.data.database.model.Game
import com.app.cs377final.data.repository.GameRepository
import com.app.cs377final.network.model.GameResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamesAdapter
    (
    private val games: List<GameResponse>?,
    private val repository : GameRepository
) : RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    class GamesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameTitleTextView: TextView = view.findViewById(R.id.gameNameTextView)
        val gamePriceTextView: TextView = view.findViewById(R.id.gamePriceTextView)
        val gameWhereToBuyTextView: TextView = view.findViewById(R.id.gameWhereToBuyTextView)
        val favoriteButton : Button = view.findViewById(R.id.favoriteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.videogame_item, parent, false)
        return GamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val game = games?.get(position)

        if (game != null) {
            holder.gameTitleTextView.text = game.title

            holder.gamePriceTextView.text = game.normalPrice

            holder.gameWhereToBuyTextView.text = game.storeID

            holder.favoriteButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val save_game = Game(
                        gameID = game.gameID,
                        normalPrice = game.normalPrice,
                        title = game.title,
                        storeID = game.storeID
                    )
                    repository.insertFavoriteGame(save_game)
                }

                Toast.makeText(holder.itemView.context, "Game added to favorites", Toast.LENGTH_SHORT).show()
                holder.favoriteButton.text = "favorited"
            }
        }

    }

    override fun getItemCount(): Int
    {
        return games?.size ?: 0
    }
}