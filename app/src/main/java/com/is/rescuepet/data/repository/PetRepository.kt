package com.`is`.rescuepet.data.repository

import com.`is`.rescuepet.data.local.dao.PetDao
import com.`is`.rescuepet.data.mapper.toDomain
import com.`is`.rescuepet.data.mapper.toEntity
import com.`is`.rescuepet.domain.model.Pet

class PetRepository(
    private val petDao: PetDao
) {

    suspend fun getAllPets(): List<Pet> {
        return petDao.getAllPets().map { it.toDomain() }
    }

    suspend fun getPetById(id: Int): Pet? {
        return petDao.getPetById(id)?.toDomain()
    }

    suspend fun searchPets(query: String): List<Pet> {
        return petDao.searchPets(query).map { it.toDomain() }
    }

    suspend fun getPetsBySpecies(species: String): List<Pet> {
        return petDao.getPetsBySpecies(species).map { it.toDomain() }
    }

    suspend fun getPetsBySize(size: String): List<Pet> {
        return petDao.getPetsBySize(size).map { it.toDomain() }
    }

    suspend fun insertPets(pets: List<Pet>) {
        petDao.insertPets(
            pets.map { it.toEntity() }
        )
    }

    suspend fun insertPet(pet: Pet) {
        petDao.insertPets(
            listOf(pet.toEntity())
        )
    }

    suspend fun updatePetAvailability(
        petId: Int,
        available: Boolean
    ) {
        petDao.updatePetAvailability(
            petId = petId,
            available = available
        )
    }
}