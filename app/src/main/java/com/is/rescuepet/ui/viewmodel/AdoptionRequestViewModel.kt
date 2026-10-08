package com.`is`.rescuepet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`is`.rescuepet.data.repository.AdoptionRequestRepository
import com.`is`.rescuepet.data.repository.PetRepository
import com.`is`.rescuepet.domain.model.AdoptionRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AdoptionRequestUiState(
    val requests: List<AdoptionRequest> = emptyList(),
    val pendingRequest: AdoptionRequest? = null,
    val approvedRequest: AdoptionRequest? = null,
    val canRequest: Boolean = true,
    val isLoading: Boolean = false,
    val message: String? = null
)

class AdoptionRequestViewModel(
    private val repository: AdoptionRequestRepository,
    private val petRepository: PetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AdoptionRequestUiState()
    )

    val uiState: StateFlow<AdoptionRequestUiState> =
        _uiState.asStateFlow()


    // ==============================
    // SOLICITUDES DEL USUARIO
    // ==============================

    fun loadUserRequests(userId: Int) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                message = null
            )

            try {

                val requests =
                    repository.getRequestsByUser(userId)

                val pending =
                    repository.getPendingRequestByUser(userId)

                val approved =
                    repository.getApprovedRequestByUser(userId)

                val canRequest =
                    pending == null && approved == null

                _uiState.value = _uiState.value.copy(
                    requests = requests,
                    pendingRequest = pending,
                    approvedRequest = approved,
                    canRequest = canRequest,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "No se pudieron cargar las solicitudes."
                )
            }
        }
    }


    // ==============================
    // CREAR SOLICITUD
    // ==============================

    fun createRequest(
        userId: Int,
        petId: Int,
        fullName: String,
        age: Int,
        phone: String,
        email: String,
        address: String,
        city: String,
        hasPreviousPets: Boolean,
        housingType: String,
        petLivingPlace: String,
        reason: String
    ) {

        viewModelScope.launch {

            try {

                val pending =
                    repository.getPendingRequestByUser(userId)

                val approved =
                    repository.getApprovedRequestByUser(userId)


                // El usuario ya tiene una solicitud pendiente
                if (pending != null) {

                    _uiState.value = _uiState.value.copy(
                        message = "YA TIENES UNA SOLICITUD PENDIENTE"
                    )

                    return@launch
                }


                // El usuario ya tiene una adopción aprobada
                if (approved != null) {

                    _uiState.value = _uiState.value.copy(
                        message = "Ya tienes una adopción aprobada."
                    )

                    return@launch
                }


                val request = AdoptionRequest(
                    id = 0,
                    userId = userId,
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
                    status = "PENDING",
                    adminMessage = null,
                    createdAt = System.currentTimeMillis().toString()
                )


                // UNA SOLA INSERCIÓN
                repository.insertRequest(request)


                // Actualizar información del usuario
                loadUserRequests(userId)


                _uiState.value = _uiState.value.copy(
                    message = "Solicitud enviada correctamente."
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    message = "No se pudo enviar la solicitud."
                )
            }
        }
    }

    fun loadPendingRequests() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                message = null
            )

            try {

                val requests =
                    repository.getPendingRequests()

                _uiState.value = _uiState.value.copy(
                    requests = requests,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "No se pudieron cargar las solicitudes."
                )
            }
        }
    }fun approveRequest(request: AdoptionRequest) {

        viewModelScope.launch {

            try {

                val approvedRequest = request.copy(
                    status = "APPROVED",
                    adminMessage = "RescuePet se pondrá en contacto contigo para agendar una cita."
                )

                repository.updateRequest(approvedRequest)

                petRepository.updatePetAvailability(
                    petId = request.petId,
                    available = false
                )

                loadPendingRequests()

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    message = "No se pudo aprobar la solicitud."
                )
            }
        }
    }


    fun rejectRequest(
        request: AdoptionRequest,
        adminMessage: String
    ) {

        viewModelScope.launch {

            try {

                val rejectedRequest = request.copy(
                    status = "REJECTED",
                    adminMessage = adminMessage
                )

                repository.updateRequest(rejectedRequest)

                loadPendingRequests()

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    message = "No se pudo rechazar la solicitud."
                )
            }
        }
    }




    // ==============================
    // LIMPIAR MENSAJE
    // ==============================

    fun clearMessage() {

        _uiState.value = _uiState.value.copy(
            message = null
        )
    }
}