package com.cartrack.app.api

import retrofit2.http.GET

interface Route {
    @GET("user")
    fun getUserDetail()
}