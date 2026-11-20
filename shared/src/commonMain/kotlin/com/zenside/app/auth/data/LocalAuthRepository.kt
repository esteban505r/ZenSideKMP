package com.zenside.app.auth.data

import com.zenside.app.auth.domain.AuthRepository
import com.zenside.app.auth.domain.User
import com.zenside.app.core.db.ZenSideDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * Local-only [AuthRepository] implementation backed by SQLDelight.
 * This is the seam that will later be swapped for a networked
 * implementation with zero changes to presentation/ui.
 */
internal class LocalAuthRepository(
    private val database: ZenSideDatabase
) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser = _currentUser.asStateFlow()

    init {
        restoreSession()
    }

    private fun restoreSession() {
        val session = database.sessionQueries.selectSession().executeAsOneOrNull()
        if (session != null) {
            val row = database.userQueries.selectUserById(session.userId).executeAsOneOrNull()
            if (row != null) {
                _currentUser.value = User(row.id, row.email, row.displayName)
            }
        }
    }

    override suspend fun register(email: String, displayName: String, password: String): Result<User> =
        withContext(Dispatchers.Default) {
            val existing = database.userQueries.selectUserByEmail(email).executeAsOneOrNull()
            if (existing != null) {
                return@withContext Result.failure(IllegalStateException("Ya existe una cuenta con ese correo."))
            }
            val id = randomId()
            val hashed = SimpleHasher.hash(password)
            database.userQueries.insertUser(id, email, displayName, hashed)
            val user = User(id, email, displayName)
            database.sessionQueries.setSession(id)
            _currentUser.value = user
            Result.success(user)
        }

    override suspend fun login(email: String, password: String): Result<User> =
        withContext(Dispatchers.Default) {
            val row = database.userQueries.selectUserByEmail(email).executeAsOneOrNull()
                ?: return@withContext Result.failure(IllegalStateException("Correo o contraseña incorrectos."))
            val hashed = SimpleHasher.hash(password)
            if (row.passwordHash != hashed) {
                return@withContext Result.failure(IllegalStateException("Correo o contraseña incorrectos."))
            }
            val user = User(row.id, row.email, row.displayName)
            database.sessionQueries.setSession(row.id)
            _currentUser.value = user
            Result.success(user)
        }

    override suspend fun logout() = withContext(Dispatchers.Default) {
        database.sessionQueries.clearSession()
        _currentUser.value = null
    }

    override suspend fun getCurrentSession(): User? = _currentUser.value

    private fun randomId(): String =
        (0 until 4).joinToString("-") { Random.nextLong(0, Long.MAX_VALUE).toString(16) }
}
