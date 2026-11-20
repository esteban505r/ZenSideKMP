package com.zenside.app.rutinas.domain

/**
 * Seam for a future networked implementation. Only a [LocalRutinaRepository]
 * exists today (derived from the posturas seed data), but presentation/ui
 * depend solely on this interface via Koin injection.
 */
interface RutinaRepository {
    suspend fun getRutinas(): List<Rutina>
}
