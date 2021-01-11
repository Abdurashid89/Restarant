package com.example.restuarant.di.provider

import com.example.restuarant.model.server.ResApi

class ApiWithChangesRegistration(
    private val serverApi: ResApi
) : ResApi by serverApi