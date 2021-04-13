package com.cartrack.app.database

import androidx.lifecycle.LiveData

class AppRepository( private val appDao: AppDao) {

    val getAll: LiveData<List<Entity>> = appDao.getAll()
    suspend fun insert(entity:Entity) = appDao.insert(entity)

    suspend fun checkUser(username:String, password: String) = appDao.checkUser(username, password)
}