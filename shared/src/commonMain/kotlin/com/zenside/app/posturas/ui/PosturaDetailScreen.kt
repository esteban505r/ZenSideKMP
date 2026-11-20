package com.zenside.app.posturas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zenside.app.posturas.presentation.PosturaDetailUiState

@Composable
fun PosturaDetailScreen(
    state: PosturaDetailUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
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
                Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
            }

            state.postura != null -> {
                val postura = state.postura
                Text(text = postura.nombre, style = MaterialTheme.typography.headlineSmall)
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Text(text = postura.categoria, style = MaterialTheme.typography.labelLarge)
                    Text(text = " · ${postura.tiempo}", style = MaterialTheme.typography.labelLarge)
                }
                Text(
                    text = postura.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "Recomendaciones",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 20.dp)
                )
                LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                    items(postura.recomendaciones) { recomendacion ->
                        Text(text = "• $recomendacion", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}
