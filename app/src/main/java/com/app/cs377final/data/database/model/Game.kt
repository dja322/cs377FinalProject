package com.app.cs377final.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Games")
data class Game (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gameID: String = "",
    val normalPrice: String = "",
    val title: String = "",
    val storeID: String = ""
)