package com.zenside.app.posturas.domain

data class Postura(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val tiempo: String,
    val descripcion: String,
    val recomendaciones: List<String>
)
