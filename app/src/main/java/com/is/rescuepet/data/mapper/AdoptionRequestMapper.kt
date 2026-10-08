package com.`is`.rescuepet.data.mapper

import com.`is`.rescuepet.data.local.entity.AdoptionRequestEntity
import com.`is`.rescuepet.domain.model.AdoptionRequest

fun AdoptionRequestEntity.toDomain(): AdoptionRequest {
    return AdoptionRequest(
        id = id,
        userId = userId,
        firebaseUserId = firebaseUserId,
        petId = petId,
        fullName = fullName,
        age = age,
        phone = phone,
        email = email,
        address = address,
        city = city,
        hasPreviousPets = hasPreviousPets,
        housingType = housingType,
        petLivingPlace = petLivingPlace,
        reason = reason,
        status = status,
        adminMessage = adminMessage,
        createdAt = createdAt
    )
}

fun AdoptionRequest.toEntity(): AdoptionRequestEntity {
    return AdoptionRequestEntity(
        id = id,
        userId = userId,
        firebaseUserId = firebaseUserId,
        petId = petId,
        fullName = fullName,
        age = age,
        phone = phone,
        email = email,
        address = address,
        city = city,
        hasPreviousPets = hasPreviousPets,
        housingType = housingType,
        petLivingPlace = petLivingPlace,
        reason = reason,
        status = status,
        adminMessage = adminMessage,
        createdAt = createdAt
    )
}