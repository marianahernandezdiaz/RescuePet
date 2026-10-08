package com.`is`.rescuepet.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uid: String = "",
    val name: String,
    val email: String,
    val password: String,
    val role: String = "USER"
)