package com.cartrack.app.database

import android.content.Context
import android.content.Entity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun Dao(): Dao

    companion object {
        private const val DB_NAME = "cartrack.db"

        var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = INSTANCE
            ?: synchronized(LOCK){
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}