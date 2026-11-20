package com.zenside.app.posturas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenside.app.posturas.domain.Postura
import com.zenside.app.posturas.domain.PosturaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PosturasListUiState(
    val isLoading: Boolean = true,
    val posturas: List<Postura> = emptyList(),
    val categorias: List<String> = emptyList(),
    val selectedCategoria: String? = null,
    val errorMessage: String? = null
) {
    val filteredPosturas: List<Postura>
        get() = selectedCategoria?.let { categoria ->
            posturas.filter { it.categoria == categoria }
        } ?: posturas
}

class PosturasListViewModel(
    private val repository: PosturaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PosturasListUiState())
    val uiState: StateFlow<PosturasListUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val posturas = repository.getPosturas()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    posturas = posturas,
                    categorias = posturas.map { it.categoria }.distinct()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun onCategoriaSelected(categoria: String?) {
        _uiState.value = _uiState.value.copy(selectedCategoria = categoria)
    }
}
