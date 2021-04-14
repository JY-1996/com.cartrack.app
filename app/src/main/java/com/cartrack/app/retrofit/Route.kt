package com.cartrack.app.retrofit

import com.cartrack.app.data.detail.UserContent.User
import retrofit2.Response
import retrofit2.http.GET

interface Route {
    @GET("/users")
    suspend fun getDetail(): Response<List<User>>
}