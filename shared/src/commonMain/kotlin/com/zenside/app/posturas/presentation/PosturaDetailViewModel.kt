package com.zenside.app.posturas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenside.app.posturas.domain.Postura
import com.zenside.app.posturas.domain.PosturaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PosturaDetailUiState(
    val isLoading: Boolean = true,
    val postura: Postura? = null,
    val errorMessage: String? = null
)

class PosturaDetailViewModel(
    private val repository: PosturaRepository,
    private val posturaId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(PosturaDetailUiState())
    val uiState: StateFlow<PosturaDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val postura = repository.getPosturaById(posturaId)
            _uiState.value = if (postura != null) {
                _uiState.value.copy(isLoading = false, postura = postura)
            } else {
                _uiState.value.copy(isLoading = false, errorMessage = "Postura no encontrada.")
            }
        }
    }
}
