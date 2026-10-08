package com.`is`.rescuepet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adoption_requests")
data class AdoptionRequestEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,

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