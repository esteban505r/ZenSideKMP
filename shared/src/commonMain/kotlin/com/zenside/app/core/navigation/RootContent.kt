package com.zenside.app.core.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zenside.app.auth.presentation.LoginUiState
import com.zenside.app.auth.presentation.LoginViewModel
import com.zenside.app.auth.presentation.RegisterViewModel
import com.zenside.app.auth.ui.LoginScreen
import com.zenside.app.auth.ui.RegisterScreen
import com.zenside.app.posturas.presentation.PosturaDetailViewModel
import com.zenside.app.posturas.presentation.PosturasListViewModel
import com.zenside.app.posturas.ui.PosturaDetailScreen
import com.zenside.app.posturas.ui.PosturasListScreen
import com.zenside.app.rutinas.presentation.RutinasListViewModel
import com.zenside.app.rutinas.ui.RutinasListScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RootContent(component: RootComponent) {
    val childStack by component.stack.subscribeAsState()

    Children(stack = childStack) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Login -> {
                val viewModel: LoginViewModel = koinViewModel()
                val state: LoginUiState by viewModel.uiState.collectAsState()
                LoginScreen(
                    state = state,
                    onEmailChanged = viewModel::onEmailChanged,
                    onPasswordChanged = viewModel::onPasswordChanged,
                    onLoginClicked = viewModel::onLoginClicked,
                    onNavigateToRegister = instance.component::onNavigateToRegister
                )
                LaunchedEffect(state.loggedInUser) {
                    state.loggedInUser?.let { user -> instance.component.onLoggedIn(user) }
                }
            }

            is RootComponent.Child.Register -> {
                val viewModel: RegisterViewModel = koinViewModel()
                val state by viewModel.uiState.collectAsState()
                RegisterScreen(
                    state = state,
                    onEmailChanged = viewModel::onEmailChanged,
                    onDisplayNameChanged = viewModel::onDisplayNameChanged,
                    onPasswordChanged = viewModel::onPasswordChanged,
                    onRegisterClicked = viewModel::onRegisterClicked,
                    onNavigateToLogin = instance.component::onNavigateToLogin
                )
                LaunchedEffect(state.registeredUser) {
                    state.registeredUser?.let { user -> instance.component.onRegistered(user) }
                }
            }

            is RootComponent.Child.Main -> MainContent(instance.component)
        }
    }
}

@Composable
private fun MainContent(component: MainComponent) {
    val childStack by component.stack.subscribeAsState()
    val selectedTab by component.selectedTab.subscribeAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == MainTab.POSTURAS,
                    onClick = { component.onTabSelected(MainTab.POSTURAS) },
                    icon = {},
                    label = { Text("Posturas") }
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.RUTINAS,
                    onClick = { component.onTabSelected(MainTab.RUTINAS) },
                    icon = {},
                    label = { Text("Rutinas") }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Children(stack = childStack) { child ->
                when (val instance = child.instance) {
                    is MainComponent.Child.PosturasList -> {
                        val viewModel: PosturasListViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        PosturasListScreen(
                            state = state,
                            onCategoriaSelected = viewModel::onCategoriaSelected,
                            onPosturaClicked = instance.component::onPosturaClicked
                        )
                    }

                    is MainComponent.Child.PosturaDetail -> {
                        val viewModel: PosturaDetailViewModel = koinViewModel { parametersOf(instance.component.posturaId) }
                        val state by viewModel.uiState.collectAsState()
                        PosturaDetailScreen(state = state)
                    }

                    is MainComponent.Child.RutinasList -> {
                        val viewModel: RutinasListViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        RutinasListScreen(state = state)
                    }
                }
            }
        }
    }
}
