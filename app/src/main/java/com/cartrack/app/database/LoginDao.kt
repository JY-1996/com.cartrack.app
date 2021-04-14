package com.cartrack.app.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface LoginDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<Entity>>

    @Insert
    fun insertAll(entities: List<Entity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity:Entity)

    @Query("SELECT * FROM user where username = :username and password = :password and country = :country")
    suspend fun checkUser(username:String, password:String, country:String): Entity?

}