package com.`is`.rescuepet.ui.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`is`.rescuepet.data.repository.PetRepository
import com.`is`.rescuepet.domain.model.Pet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class PetUiState(
    val pets: List<Pet> = emptyList(),
    val selectedPet: Pet? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class PetViewModel(
    private val petRepository: PetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetUiState())

    val uiState: StateFlow<PetUiState> = _uiState.asStateFlow()





    fun loadPets() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val pets = petRepository.getAllPets()

                _uiState.value = _uiState.value.copy(
                    pets = pets,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudieron cargar las mascotas"
                )
            }
        }
    }

    fun loadPetById(id: Int) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {

                val pet = petRepository.getPetById(id)

                _uiState.value = _uiState.value.copy(
                    selectedPet = pet,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudo cargar la mascota"
                )
            }
        }
    }

    fun searchPets(query: String) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val pets = if (query.isBlank()) {
                    petRepository.getAllPets()
                } else {
                    petRepository.searchPets(query)
                }

                _uiState.value = _uiState.value.copy(
                    pets = pets,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudieron buscar las mascotas"
                )
            }
        }
    }
    fun filterBySpecies(species: String) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val pets = petRepository.getPetsBySpecies(species)

                _uiState.value = _uiState.value.copy(
                    pets = pets,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudieron filtrar las mascotas"
                )
            }
        }
    }
    fun filterBySize(size: String) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val pets = petRepository.getPetsBySize(size)

                _uiState.value = _uiState.value.copy(
                    pets = pets,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudieron filtrar las mascotas"
                )
            }
        }
    }

    fun createPet(
        name: String,
        species: String,
        breed: String,
        age: Int,
        size: String,
        description: String,
        imageUri: String?,
        latitude: Double,
        longitude: Double,
        available: Boolean
    ) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {

                val pet = Pet(
                    id = 0,
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

                petRepository.insertPet(pet)

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudo registrar la mascota"
                )
            }
        }
    }

    fun updatePetAvailability(
        petId: Int,
        available: Boolean
    ) {
        viewModelScope.launch {

            try {

                petRepository.updatePetAvailability(
                    petId = petId,
                    available = available
                )

                // Actualizamos la lista que está mostrando la pantalla
                val updatedPets = petRepository.getAllPets()

                _uiState.value = _uiState.value.copy(
                    pets = updatedPets,
                    error = null
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    error = "No se pudo actualizar la disponibilidad"
                )
            }
        }
    }
}