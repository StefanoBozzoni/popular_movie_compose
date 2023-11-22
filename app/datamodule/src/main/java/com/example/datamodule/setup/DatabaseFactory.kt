package com.example.datamodule.setup

import android.content.Context
import androidx.room.Room
import com.example.datamodule.data.local.database.AppDatabase
import com.example.datamodule.data.local.database.DatabaseConstants
import com.example.datamodule.data.remote.AppService
import org.koin.android.ext.koin.androidContext

class DatabaseFactory {
    fun getOrCreateDatabaseInstance(appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, DatabaseConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

}

