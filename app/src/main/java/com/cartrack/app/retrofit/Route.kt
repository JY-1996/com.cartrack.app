package com.cartrack.app.retrofit

import com.cartrack.app.data.detail.UserContent.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface Route {
    @GET("users")
    fun getDetail(): Response<List<User>>

    @GET("users")
    fun getDetail2(): Response<ResponseBody>
}