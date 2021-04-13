package com.cartrack.app.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<Entity>>

    @Insert
    fun insert(username:String, country:String, password:String): LiveData<Entity>

    @Query("SELECT * FROM user where username = :username and password = :password")
    fun checkUser(username:String,password:String): LiveData<Entity>
}