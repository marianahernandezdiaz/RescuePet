package com.`is`.rescuepet.data.repository

import com.`is`.rescuepet.data.local.dao.AdoptionRequestDao
import com.`is`.rescuepet.data.mapper.toDomain
import com.`is`.rescuepet.data.mapper.toEntity
import com.`is`.rescuepet.domain.model.AdoptionRequest

class AdoptionRequestRepository(
    private val adoptionRequestDao: AdoptionRequestDao
) {

    suspend fun insertRequest(
        request: AdoptionRequest
    ) {
        adoptionRequestDao.insertRequest(
            request.toEntity()
        )
    }

    suspend fun getRequestsByUser(
        userId: Int
    ): List<AdoptionRequest> {
        return adoptionRequestDao
            .getRequestsByUser(userId)
            .map { it.toDomain() }
    }

    suspend fun getPendingRequestByUser(
        userId: Int
    ): AdoptionRequest? {
        return adoptionRequestDao
            .getPendingRequestByUser(userId)
            ?.toDomain()
    }

    suspend fun getApprovedRequestByUser(
        userId: Int
    ): AdoptionRequest? {
        return adoptionRequestDao
            .getApprovedRequestByUser(userId)
            ?.toDomain()
    }

    suspend fun getPendingRequestByPet(
        petId: Int
    ): AdoptionRequest? {
        return adoptionRequestDao
            .getPendingRequestByPet(petId)
            ?.toDomain()
    }

    suspend fun getPendingRequests(): List<AdoptionRequest> {
        return adoptionRequestDao
            .getPendingRequests()
            .map { it.toDomain() }
    }

    suspend fun getRequestById(
        id: Int
    ): AdoptionRequest? {
        return adoptionRequestDao
            .getRequestById(id)
            ?.toDomain()
    }

    suspend fun updateRequest(
        request: AdoptionRequest
    ) {
        adoptionRequestDao.updateRequest(
            request.toEntity()
        )
    }
}