package com.`is`.rescuepet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val species: String,
    val breed: String,
    val age: Int,
    val size: String,
    val description: String,
    val imageUri: String?,
    val latitude: Double,
    val longitude: Double,
    val available: Boolean
)