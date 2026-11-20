package com.zenside.app.rutinas.domain

import com.zenside.app.posturas.domain.Postura

data class Rutina(
    val id: Int,
    val nombre: String,
    val posturas: List<Postura>
)
