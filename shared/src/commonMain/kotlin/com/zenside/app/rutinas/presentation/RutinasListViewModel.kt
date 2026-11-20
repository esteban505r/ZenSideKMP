package com.zenside.app.rutinas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenside.app.rutinas.domain.Rutina
import com.zenside.app.rutinas.domain.RutinaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RutinasListUiState(
    val isLoading: Boolean = true,
    val rutinas: List<Rutina> = emptyList(),
    val errorMessage: String? = null
)

class RutinasListViewModel(
    private val repository: RutinaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RutinasListUiState())
    val uiState: StateFlow<RutinasListUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val rutinas = repository.getRutinas()
                _uiState.value = _uiState.value.copy(isLoading = false, rutinas = rutinas)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}
