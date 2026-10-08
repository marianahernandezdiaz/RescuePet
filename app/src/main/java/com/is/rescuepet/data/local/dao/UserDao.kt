package com.`is`.rescuepet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.`is`.rescuepet.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun countUserByEmail(email: String): Int

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(
        email: String,
        password: String
    ): UserEntity?
}