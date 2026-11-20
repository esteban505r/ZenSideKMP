package com.zenside.app.rutinas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zenside.app.rutinas.domain.Rutina
import com.zenside.app.rutinas.presentation.RutinasListUiState

@Composable
fun RutinasListScreen(
    state: RutinasListUiState,
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
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(state.rutinas) { rutina ->
                        RutinaCard(rutina = rutina)
                    }
                }
            }
        }
    }
}

@Composable
private fun RutinaCard(rutina: Rutina) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = rutina.nombre, style = MaterialTheme.typography.titleMedium)
            rutina.posturas.forEach { postura ->
                Text(
                    text = "• ${postura.nombre} (${postura.tiempo})",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}
