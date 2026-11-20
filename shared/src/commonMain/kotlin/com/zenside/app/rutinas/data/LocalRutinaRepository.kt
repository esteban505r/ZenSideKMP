package com.zenside.app.rutinas.data

import com.zenside.app.posturas.domain.PosturaRepository
import com.zenside.app.rutinas.domain.Rutina
import com.zenside.app.rutinas.domain.RutinaRepository

/**
 * Derives a handful of sample rutinas from the posturas seed data by
 * grouping poses by categoria. No separate JSON needed for v1.
 */
internal class LocalRutinaRepository(
    private val posturaRepository: PosturaRepository
) : RutinaRepository {

    override suspend fun getRutinas(): List<Rutina> {
        val posturas = posturaRepository.getPosturas()
        return posturas
            .groupBy { it.categoria }
            .entries
            .mapIndexed { index, (categoria, posturasDeCategoria) ->
                Rutina(
                    id = index,
                    nombre = "Rutina de $categoria",
                    posturas = posturasDeCategoria
                )
            }
    }
}
