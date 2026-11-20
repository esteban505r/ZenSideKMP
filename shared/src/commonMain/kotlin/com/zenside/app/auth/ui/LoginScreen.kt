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
import com.zenside.app.auth.presentation.LoginUiState

@Composable
fun LoginScreen(
    state: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ZenSide", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Yoga y bienestar para niños", style = MaterialTheme.typography.bodyMedium)

        Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChanged,
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
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
                onClick = onLoginClicked,
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(2.dp))
                } else {
                    Text("Entrar")
                }
            }

            TextButton(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}
