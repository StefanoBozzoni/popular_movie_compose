package com.example.datamodule.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.datamodule.data.local.database.DatabaseConstants

@androidx.room.Entity(tableName = com.example.datamodule.data.local.database.DatabaseConstants.TABLE_FAVORITES)
data class FavoritesItem (
    @androidx.room.PrimaryKey
    val id: Int,
    val posterPath: String,
)
