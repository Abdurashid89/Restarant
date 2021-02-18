package com.example.restuarant.model.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.restuarant.model.entities.UnPaidData
import com.example.restuarant.model.storage.dao.UnPaidDao

/**
 * Created by Shohboz Qoraboyev on 17,Февраль,2021
 */

@Database(entities = [UnPaidData::class],version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun unPaidDao() : UnPaidDao
}