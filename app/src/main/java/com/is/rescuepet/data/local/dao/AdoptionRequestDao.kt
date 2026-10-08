package com.`is`.rescuepet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.`is`.rescuepet.data.local.entity.AdoptionRequestEntity

@Dao
interface AdoptionRequestDao {

    @Insert
    suspend fun insertRequest(
        request: AdoptionRequestEntity
    )

    @Query("""
        SELECT * FROM adoption_requests
        WHERE userId = :userId
        ORDER BY id DESC
    """)
    suspend fun getRequestsByUser(
        userId: Int
    ): List<AdoptionRequestEntity>

    @Query("""
        SELECT * FROM adoption_requests
        WHERE userId = :userId
        AND status = 'PENDING'
        LIMIT 1
    """)
    suspend fun getPendingRequestByUser(
        userId: Int
    ): AdoptionRequestEntity?

    @Query("""
        SELECT * FROM adoption_requests
        WHERE userId = :userId
        AND status = 'APPROVED'
        LIMIT 1
    """)
    suspend fun getApprovedRequestByUser(
        userId: Int
    ): AdoptionRequestEntity?

    @Query("""
        SELECT * FROM adoption_requests
        WHERE petId = :petId
        AND status = 'PENDING'
        LIMIT 1
    """)
    suspend fun getPendingRequestByPet(
        petId: Int
    ): AdoptionRequestEntity?

    @Query("""
        SELECT * FROM adoption_requests
        WHERE status = 'PENDING'
        ORDER BY id DESC
    """)
    suspend fun getPendingRequests(): List<AdoptionRequestEntity>

    @Query("""
        SELECT * FROM adoption_requests
        WHERE id = :id
        LIMIT 1
    """)
    suspend fun getRequestById(
        id: Int
    ): AdoptionRequestEntity?

    @Update
    suspend fun updateRequest(
        request: AdoptionRequestEntity
    )
}