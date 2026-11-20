package com.zenside.app.posturas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zenside.app.posturas.domain.Postura
import com.zenside.app.posturas.presentation.PosturasListUiState

@Composable
fun PosturasListScreen(
    state: PosturasListUiState,
    onCategoriaSelected: (String?) -> Unit,
    onPosturaClicked: (Postura) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                LazyRow(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    item {
                        FilterChip(
                            selected = state.selectedCategoria == null,
                            onClick = { onCategoriaSelected(null) },
                            label = { Text("Todas") },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    items(state.categorias) { categoria ->
                        FilterChip(
                            selected = state.selectedCategoria == categoria,
                            onClick = { onCategoriaSelected(categoria) },
                            label = { Text(categoria) },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                    items(state.filteredPosturas) { postura ->
                        PosturaRow(postura = postura, onClick = { onPosturaClicked(postura) })
                    }
                }
            }
        }
    }
}

@Composable
private fun PosturaRow(postura: Postura, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = postura.nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = "${postura.categoria} · ${postura.tiempo}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
