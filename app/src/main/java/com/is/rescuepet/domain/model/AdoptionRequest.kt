package com.`is`.rescuepet.domain.model

data class AdoptionRequest(
    val id: Int,
    val userId: Int,
    val firebaseUserId: String = "",
    val petId: Int,
    val fullName: String,
    val age: Int,
    val phone: String,
    val email: String,
    val address: String,
    val city: String,
    val hasPreviousPets: Boolean,
    val housingType: String,
    val petLivingPlace: String,
    val reason: String,
    val status: String = "PENDING",
    val adminMessage: String? = null,
    val createdAt: String
)