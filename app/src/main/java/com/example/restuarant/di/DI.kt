package com.example.restuarant.di

import com.example.restuarant.extentions.convertLongToTime
import com.example.restuarant.extentions.currentTimeToLong

object DI {
    const val APP_SCOPE = "app_scope"
    const val SERVER_SCOPE = "server_scope"
    const val ORDER_PROGRESS: Int = 1
    const val MENU_ITEMS_PROGRESS: Int = 2
    const val TABLES_PROGRESS: Int = 3
    val CURRENT_MILLISECOND = System.currentTimeMillis()
}