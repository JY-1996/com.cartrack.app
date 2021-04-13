package com.cartrack.app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
data class Entity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var username: String = "",
    var password: String = "",
    var country: String = "",
    var createdOn: Date = Date()
)