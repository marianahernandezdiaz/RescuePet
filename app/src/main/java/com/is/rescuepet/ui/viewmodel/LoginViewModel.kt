package com.`is`.rescuepet.ui.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`is`.rescuepet.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val role: String? = null,
    val userId: Int? = null
)

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email
        )
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password
        )
    }

    fun login() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            val currentState = _uiState.value

            if (
                currentState.email.isBlank() ||
                currentState.password.isBlank()
            ) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "El correo y la contraseña son obligatorios"
                )
                return@launch
            }

            try {
                val user = userRepository.login(
                    email = currentState.email,
                    password = currentState.password
                )

                if (user != null) {

                    _uiState.value = currentState.copy(
                        isLoading = false,
                        success = true,
                        role = user.role,
                        userId = user.id

                    )

                } else {

                    _uiState.value = currentState.copy(
                        isLoading = false,
                        error = "Correo o contraseña incorrectos"
                    )
                }

            } catch (e: Exception) {

                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "Ocurrió un error al iniciar sesión"
                )
            }
        }
    }
}