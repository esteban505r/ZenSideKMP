package com.zenside.app.core.di

import com.zenside.app.auth.data.LocalAuthRepository
import com.zenside.app.auth.domain.AuthRepository
import com.zenside.app.auth.domain.GetCurrentSessionUseCase
import com.zenside.app.auth.domain.LoginUseCase
import com.zenside.app.auth.domain.LogoutUseCase
import com.zenside.app.auth.domain.RegisterUseCase
import com.zenside.app.auth.presentation.LoginViewModel
import com.zenside.app.auth.presentation.RegisterViewModel
import com.zenside.app.core.db.ZenSideDatabase
import com.zenside.app.posturas.data.LocalPosturaRepository
import com.zenside.app.posturas.domain.PosturaRepository
import com.zenside.app.posturas.presentation.PosturaDetailViewModel
import com.zenside.app.posturas.presentation.PosturasListViewModel
import com.zenside.app.rutinas.data.LocalRutinaRepository
import com.zenside.app.rutinas.domain.RutinaRepository
import com.zenside.app.rutinas.presentation.RutinasListViewModel
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/** One Koin module per feature. Each binds the Local* implementation to its domain interface. */
val coreModule = module {
    single { ZenSideDatabase(get()) }
    single { Json { ignoreUnknownKeys = true } }
}

val authModule = module {
    single<AuthRepository> { LocalAuthRepository(get()) }
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { GetCurrentSessionUseCase(get()) }
    factory { LogoutUseCase(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}

val posturasModule = module {
    single<PosturaRepository> { LocalPosturaRepository(get()) }
    viewModel { PosturasListViewModel(get()) }
    viewModel { (posturaId: Int) -> PosturaDetailViewModel(get(), posturaId) }
}

val rutinasModule = module {
    single<RutinaRepository> { LocalRutinaRepository(get()) }
    viewModel { RutinasListViewModel(get()) }
}

/** All feature modules, loaded together with the platform module at app startup. */
val featureModules = listOf(coreModule, authModule, posturasModule, rutinasModule)
