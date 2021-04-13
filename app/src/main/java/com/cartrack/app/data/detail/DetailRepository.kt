package com.cartrack.app.data.detail

import com.cartrack.app.retrofit.RetrofitClientInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class DetailRepository {
    suspend fun getDetail(): Response<List<UserContent.User>> {
        return RetrofitClientInstance.route.getDetail()
    }
    suspend fun getDetail2(): Response<ResponseBody> {
        return RetrofitClientInstance.route.getDetail2()
    }
}