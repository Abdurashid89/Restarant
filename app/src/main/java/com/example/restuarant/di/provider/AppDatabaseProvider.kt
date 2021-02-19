package com.example.restuarant.di.provider

import android.content.Context
import androidx.room.Room
import com.example.restuarant.model.storage.AppDatabase
import javax.inject.Inject
import javax.inject.Provider

class AppDatabaseProvider @Inject constructor(
    private val context: Context
):Provider<AppDatabase> {
    override fun get(): AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java,"database").build()
    }
}