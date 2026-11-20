package com.zenside.app.auth.domain

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> =
        repository.login(email, password)
}

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, displayName: String, password: String): Result<User> =
        repository.register(email, displayName, password)
}

class GetCurrentSessionUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): User? = repository.getCurrentSession()
}

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logout()
}
