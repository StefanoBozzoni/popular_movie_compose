package com.example.mycomposem3playground.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycomposem3playground.data.local.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_FAVORITES)
data class FavoritesItem (
    @PrimaryKey
    val id: Int,
    val posterPath: String,
)
