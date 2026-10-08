package com.`is`.rescuepet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet_reports")
data class PetReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val species: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUri: String?,
    val date: String,
    val status: String
)