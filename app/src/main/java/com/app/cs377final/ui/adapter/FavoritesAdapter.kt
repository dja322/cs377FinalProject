package com.app.cs377final.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.cs377final.R
import com.app.cs377final.data.database.model.Game
import com.app.cs377final.data.repository.GameRepository
import com.app.cs377final.network.model.GameResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesAdapter (
    private val games: List<Game>,
    private val repository : GameRepository
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameTitleTextView: TextView = view.findViewById(R.id.gameNameTextView)
        val gamePriceTextView: TextView = view.findViewById(R.id.gamePriceTextView)
        val gameWhereToBuyTextView: TextView = view.findViewById(R.id.gameWhereToBuyTextView)
        val favoriteButton : Button = view.findViewById(R.id.favoriteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.videogame_item, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val game = games[position]

        holder.favoriteButton.text = "unfavorite"

        holder.gameTitleTextView.text = game.title

        holder.gamePriceTextView.text = game.normalPrice

        holder.gameWhereToBuyTextView.text = game.storeID

        holder.favoriteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                repository.deleteGame(game)
            }
        }


    }

    override fun getItemCount(): Int = games.size

}