package com.zenside.app.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenside.app.auth.domain.RegisterUseCase
import com.zenside.app.auth.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val email: String = "",
    val displayName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registeredUser: User? = null
)

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(email = value, errorMessage = null)
    }

    fun onDisplayNameChanged(value: String) {
        _uiState.value = _uiState.value.copy(displayName = value, errorMessage = null)
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value, errorMessage = null)
    }

    fun onRegisterClicked() {
        val state = _uiState.value
        if (state.email.isBlank() || state.displayName.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Completa todos los campos.")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = registerUseCase(state.email.trim(), state.displayName.trim(), state.password)
            _uiState.value = result.fold(
                onSuccess = { user -> _uiState.value.copy(isLoading = false, registeredUser = user) },
                onFailure = { error -> _uiState.value.copy(isLoading = false, errorMessage = error.message) }
            )
        }
    }
}
