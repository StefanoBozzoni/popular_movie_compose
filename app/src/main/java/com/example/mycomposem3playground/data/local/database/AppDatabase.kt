package com.example.mycomposem3playground.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycomposem3playground.data.local.dao.FavoritesDao
import com.example.mycomposem3playground.data.local.model.FavoritesItem

@Database(entities = [FavoritesItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
