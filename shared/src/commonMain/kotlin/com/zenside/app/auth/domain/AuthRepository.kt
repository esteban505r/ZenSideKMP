package com.zenside.app.auth.domain

import kotlinx.coroutines.flow.Flow

/**
 * Seam for a future networked implementation.
 * Only a [LocalAuthRepository] exists today, but ViewModels and UI
 * depend solely on this interface via Koin injection.
 */
interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun register(email: String, displayName: String, password: String): Result<User>

    suspend fun login(email: String, password: String): Result<User>

    suspend fun logout()

    suspend fun getCurrentSession(): User?
}
