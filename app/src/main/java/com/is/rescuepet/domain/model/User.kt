package com.`is`.rescuepet.domain.model

data class User(
    val id: Int = 0,
    val uid: String = "",
    val name: String,
    val email: String,
    val role: String = "USER"
)