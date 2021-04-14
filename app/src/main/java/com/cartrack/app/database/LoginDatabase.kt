package com.cartrack.app.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun getLoginDao(): LoginDao

    companion object {
        private const val DB_NAME = "login_database.db"

        @Volatile
        private var instance: LoginDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            LoginDatabase::class.java,
            DB_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadExecutor().execute {
                    instance?.let {
                            it.getLoginDao().insertAll(DummyDataGenerator.getUsers())
                    }
                }
            }
        }).build()
    }
}