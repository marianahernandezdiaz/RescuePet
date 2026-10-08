package com.`is`.rescuepet.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String = "USER"
)