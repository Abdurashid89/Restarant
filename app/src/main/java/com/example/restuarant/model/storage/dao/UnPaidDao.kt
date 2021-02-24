package com.example.restuarant.model.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restuarant.model.entities.UnPaidData

/**
 * Created by Shohboz Qoraboyev on 17,Февраль,2021
 */

@Dao
interface UnPaidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<UnPaidData>)

    @Query("select * from UnPaidData")
    fun getAllUnPaidOrder():List<UnPaidData>
}