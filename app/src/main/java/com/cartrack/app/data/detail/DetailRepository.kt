package com.cartrack.app.data.detail

import com.cartrack.app.retrofit.RetrofitClientInstance
import retrofit2.Response

class DetailRepository {
    suspend fun getDetail(): Response<List<UserContent.User>> {
        return RetrofitClientInstance.route.getDetail()
    }
}