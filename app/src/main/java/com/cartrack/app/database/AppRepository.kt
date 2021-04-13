package com.cartrack.app.database

import androidx.lifecycle.LiveData

class AppRepository( private val appDatabase: AppDatabase) {

    val getAll: LiveData<List<Entity>> = appDatabase.Dao().getAll()
    suspend fun insert(username:String, country:String,password: String) = appDatabase.Dao().insert(username, country, password)

    suspend fun checkUser(username:String, password: String) = appDatabase.Dao().checkUser(username, password)
}