package com.zenside.app.posturas.data

import com.zenside.app.posturas.domain.Postura
import kotlinx.serialization.Serializable

@Serializable
internal data class PosturaDto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val tiempo: String,
    val descripcion: String,
    val recomendaciones: List<String> = emptyList()
)

internal fun PosturaDto.toDomain(): Postura = Postura(
    id = id,
    nombre = nombre,
    categoria = categoria,
    tiempo = tiempo,
    descripcion = descripcion,
    recomendaciones = recomendaciones
)
