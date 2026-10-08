package com.`is`.rescuepet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`is`.rescuepet.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())

    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            val currentState = _uiState.value

            if (currentState.name.isBlank() ||
                currentState.email.isBlank() ||
                currentState.password.isBlank() ||
                currentState.confirmPassword.isBlank()
            ) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "Todos los campos son obligatorios"
                )
                return@launch
            }

            if (currentState.password != currentState.confirmPassword) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "Las contraseñas no coinciden"
                )
                return@launch
            }

            try {
                userRepository.register(
                    name = currentState.name,
                    email = currentState.email,
                    password = currentState.password
                )

                _uiState.value = currentState.copy(
                    isLoading = false,
                    success = true
                )

            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "No se pudo registrar el usuario"
                )
            }
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }
}