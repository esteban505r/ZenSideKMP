package com.zenside.app.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.zenside.app.auth.presentation.RegisterUiState
import com.zenside.app.core.ui.components.ZenAuthCard
import com.zenside.app.core.ui.components.ZenTextField

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
    val nameFocus = remember { FocusRequester() }
    val emailFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }

    LaunchedEffect(Unit) { nameFocus.requestFocus() }

    ZenAuthCard(
        title = "Crea tu cuenta",
        subtitle = "Únete y empieza tu rutina de bienestar",
        modifier = modifier.fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            ZenTextField(
                value = state.displayName,
                onValueChange = onDisplayNameChanged,
                label = "Nombre",
                imeAction = ImeAction.Next,
                focusRequester = nameFocus
            )
            ZenTextField(
                value = state.email,
                onValueChange = onEmailChanged,
                label = "Correo",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                focusRequester = emailFocus
            )
            ZenTextField(
                value = state.password,
                onValueChange = onPasswordChanged,
                label = "Contraseña",
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                focusRequester = passwordFocus,
                isError = state.errorMessage != null,
                onSubmit = onRegisterClicked
            )
        }

        state.errorMessage?.let { message ->
            Text(text = message, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Button(
            onClick = onRegisterClicked,
            enabled = !state.isLoading,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.height(20.dp), color = Color.White)
            } else {
                Text("Registrarme")
            }
        }

        TextButton(onClick = onNavigateToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Ya tengo cuenta, iniciar sesión")
        }
    }
}
