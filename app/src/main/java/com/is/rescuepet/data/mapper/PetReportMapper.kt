package com.`is`.rescuepet.data.mapper



import com.`is`.rescuepet.data.local.entity.PetReportEntity
import com.`is`.rescuepet.domain.model.PetReport

fun PetReportEntity.toDomain(): PetReport {
    return PetReport(
        id = id,
        species = species,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUri = imageUri,
        date = date,
        status = status
    )
}

fun PetReport.toEntity(): PetReportEntity {
    return PetReportEntity(
        id = id,
        species = species,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUri = imageUri,
        date = date,
        status = status
    )
}