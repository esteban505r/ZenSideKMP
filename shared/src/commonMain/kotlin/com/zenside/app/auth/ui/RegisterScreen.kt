package com.zenside.app.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zenside.app.auth.presentation.RegisterUiState

@Composable
fun RegisterScreen(
    state: RegisterUiState,
    onEmailChanged: (String) -> Unit,
    onDisplayNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Crea tu cuenta", style = MaterialTheme.typography.headlineMedium)

        Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
            OutlinedTextField(
                value = state.displayName,
                onValueChange = onDisplayNameChanged,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChanged,
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChanged,
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )

            state.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = onRegisterClicked,
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(2.dp))
                } else {
                    Text("Registrarme")
                }
            }

            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Ya tengo cuenta, iniciar sesión")
            }
        }
    }
}
