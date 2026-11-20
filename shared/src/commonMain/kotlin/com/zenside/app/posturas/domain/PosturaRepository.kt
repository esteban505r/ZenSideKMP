package com.zenside.app.posturas.domain

/**
 * Seam for a future networked implementation. Only a [LocalPosturaRepository]
 * exists today (reading the bundled seed JSON), but presentation/ui depend
 * solely on this interface via Koin injection.
 */
interface PosturaRepository {
    suspend fun getPosturas(): List<Postura>

    suspend fun getPosturaById(id: Int): Postura?
}
