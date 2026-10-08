package com.`is`.rescuepet.data.mapper


import com.`is`.rescuepet.data.local.entity.UserEntity
import com.`is`.rescuepet.domain.model.User


fun UserEntity.toDomain(): User {
    return User(
        id = id,
        uid = uid,
        name = name,
        email = email,
        role = role
    )
}

fun User.toEntity(password: String): UserEntity {
    return UserEntity(
        id = id,
        uid = uid,
        name = name,
        email = email,
        password = password,
        role = role
    )
}
