package com.`is`.rescuepet.ui.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`is`.rescuepet.data.repository.PetReportRepository
import com.`is`.rescuepet.domain.model.PetReport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class PetReportUiState(
    val species: String = "",
    val description: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val imageUri: String? = null,
    val date: String = "",
    val status: String = "Pendiente",
    val reports: List<PetReport> = emptyList(),
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

class PetReportViewModel(
    private val petReportRepository: PetReportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetReportUiState())

    val uiState: StateFlow<PetReportUiState> = _uiState.asStateFlow()

    fun setLocation(
        latitude: Double,
        longitude: Double
    ) {
        _uiState.value = _uiState.value.copy(
            latitude = latitude,
            longitude = longitude
        )
    }

    fun setSpecies(value: String) {
        _uiState.value = _uiState.value.copy(
            species = value
        )
    }

    fun setDescription(value: String) {
        _uiState.value = _uiState.value.copy(
            description = value
        )
    }

    fun setImageUri(uri: String?) {
        _uiState.value = _uiState.value.copy(
            imageUri = uri
        )
    }

    fun clearSuccess() {
        _uiState.value = _uiState.value.copy(
            success = false
        )
    }

    fun createReport() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                success = false
            )

            val currentState = _uiState.value

            if (currentState.species.isBlank()) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "La especie es obligatoria"
                )
                return@launch
            }

            if (currentState.description.isBlank()) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "La descripción es obligatoria"
                )
                return@launch
            }

            try {

                val report = PetReport(
                    id = 0,
                    species = currentState.species,
                    description = currentState.description,
                    latitude = currentState.latitude,
                    longitude = currentState.longitude,
                    imageUri = currentState.imageUri,
                    date = LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    ),
                    status = currentState.status
                )

                petReportRepository.insertReport(report)

                _uiState.value = currentState.copy(
                    isLoading = false,
                    success = true
                )

            } catch (e: Exception) {

                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "No se pudo guardar el reporte"
                )
            }
        }
    }
    fun loadReports() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {

                val reports = petReportRepository.getAllReports()

                _uiState.value = _uiState.value.copy(
                    reports = reports,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudieron cargar los reportes"
                )
            }
        }
    }

}