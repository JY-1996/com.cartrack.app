package com.cartrack.app.retrofit

import com.cartrack.app.BuildConfig
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitClientInstance {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val loginInterceptor: Interceptor
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

    private fun <T> createRetrofitService(url:String,clazz: Class<T>): T {
        val builder = OkHttpClient.Builder()
            .addInterceptor(loginInterceptor)
            .addInterceptor(BasicAuthInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
        val retro = Retrofit.Builder()
            .baseUrl(url)
//                .addConverterFactory(serializationConverterFactory(MediaType.parse("application/json; charset=utf-8")!!, JSON))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(builder.build())
            .build()

        return retro.create(clazz)
    }

    val route: Route by lazy {
        createRetrofitService(BASE_URL,Route::class.java)
    }
}

class BasicAuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("app-version", BuildConfig.VERSION_NAME)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .build()
        return chain.proceed(authenticatedRequest)
    }

}