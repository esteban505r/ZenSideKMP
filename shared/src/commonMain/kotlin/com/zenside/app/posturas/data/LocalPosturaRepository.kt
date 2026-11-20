package com.zenside.app.posturas.data

import com.zenside.app.posturas.domain.Postura
import com.zenside.app.posturas.domain.PosturaRepository
import com.zenside.app.resources.Res
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Reads the bundled seed JSON (compose resources) once and caches it in
 * memory. This is the local-only implementation behind [PosturaRepository];
 * a future networked implementation can replace this with zero changes to
 * presentation/ui.
 */
internal class LocalPosturaRepository(
    private val json: Json
) : PosturaRepository {

    private val mutex = Mutex()
    private var cache: List<Postura>? = null

    override suspend fun getPosturas(): List<Postura> = mutex.withLock {
        cache ?: load().also { cache = it }
    }

    override suspend fun getPosturaById(id: Int): Postura? =
        getPosturas().firstOrNull { it.id == id }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun load(): List<Postura> {
        val bytes = Res.readBytes("files/posturas.json")
        val text = bytes.decodeToString()
        val dtos = json.decodeFromString<List<PosturaDto>>(text)
        return dtos.map { it.toDomain() }
    }
}
