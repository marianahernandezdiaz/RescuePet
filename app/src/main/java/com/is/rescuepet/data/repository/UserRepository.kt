package com.`is`.rescuepet.data.repository



import com.`is`.rescuepet.data.local.dao.UserDao
import com.`is`.rescuepet.data.mapper.toDomain
import com.`is`.rescuepet.data.mapper.toEntity
import com.`is`.rescuepet.domain.model.User


class UserRepository(
    private val userDao: UserDao
) {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ) {
        val user = User(
            id = 0,
            name = name,
            email = email,
            role = "USER"
        )

        val userEntity = user.toEntity(password)

        userDao.insertUser(userEntity)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.toDomain()
    }

    suspend fun login(
        email: String,
        password: String
    ): User? {
        return userDao.login(email, password)?.toDomain()
    }
}