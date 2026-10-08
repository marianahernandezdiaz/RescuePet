package com.`is`.rescuepet.data.mapper


import com.`is`.rescuepet.data.local.entity.PetEntity
import com.`is`.rescuepet.domain.model.Pet

fun PetEntity.toDomain(): Pet {
    return Pet(
        id = id,
        name = name,
        species = species,
        breed = breed,
        age = age,
        size = size,
        description = description,
        imageUri = imageUri,
        latitude = latitude,
        longitude = longitude,
        available = available
    )
}

fun Pet.toEntity(): PetEntity {
    return PetEntity(
        id = id,
        name = name,
        species = species,
        breed = breed,
        age = age,
        size = size,
        description = description,
        imageUri = imageUri,
        latitude = latitude,
        longitude = longitude,
        available = available
    )
}