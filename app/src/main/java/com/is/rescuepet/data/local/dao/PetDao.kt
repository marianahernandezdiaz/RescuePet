package com.`is`.rescuepet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.`is`.rescuepet.data.local.entity.PetEntity

@Dao
interface PetDao {

    @Insert
    suspend fun insertPets(pets: List<PetEntity>)

    @Query("SELECT * FROM pets")
    suspend fun getAllPets(): List<PetEntity>

    @Query("SELECT * FROM pets WHERE id = :id")
    suspend fun getPetById(id: Int): PetEntity?

    @Query("""
        SELECT * FROM pets
        WHERE name LIKE '%' || :query || '%'
        OR breed LIKE '%' || :query || '%'
    """)
    suspend fun searchPets(query: String): List<PetEntity>

    @Query("SELECT * FROM pets WHERE species = :species")
    suspend fun getPetsBySpecies(species: String): List<PetEntity>

    @Query("UPDATE pets SET available = :available WHERE id = :petId")
    suspend fun updatePetAvailability(
        petId: Int,
        available: Boolean
    )

    @Query("SELECT * FROM pets WHERE size = :size")
    suspend fun getPetsBySize(size: String): List<PetEntity>
}