package com.cartrack.app.database

import androidx.lifecycle.LiveData

class LoginRepository( private val loginDao: LoginDao) {

   suspend fun getAll(): LiveData<List<Entity>> = loginDao.getAll()

    suspend fun insert(entity:Entity) = loginDao.insert(entity)

    suspend fun checkUser(username:String, password: String, country:String):Entity? = loginDao.checkUser(username, password,country)
}