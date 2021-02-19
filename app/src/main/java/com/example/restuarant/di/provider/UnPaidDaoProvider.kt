package com.example.restuarant.di.provider

import com.example.restuarant.model.storage.AppDatabase
import com.example.restuarant.model.storage.dao.UnPaidDao
import javax.inject.Inject
import javax.inject.Provider

class UnPaidDaoProvider @Inject constructor(
    private val database: AppDatabase
):Provider<UnPaidDao> {
    override fun get(): UnPaidDao {
        return database.unPaidDao()
    }
}
