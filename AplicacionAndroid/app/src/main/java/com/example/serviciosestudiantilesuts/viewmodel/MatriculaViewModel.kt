package com.example.serviciosestudiantilesuts.viewmodel

import androidx.lifecycle.ViewModel
import com.example.serviciosestudiantilesuts.data.Curso
import com.example.serviciosestudiantilesuts.data.Estudiante
import com.example.serviciosestudiantilesuts.data.Liquidacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MatriculaViewModel : ViewModel() {

    val cursos: List<Curso> = listOf(
        Curso(1, "Inglés Técnico", 200_000L),
        Curso(2, "Programación Web", 300_000L),
        Curso(3, "Ciberseguridad", 400_000L)
    )

    private val _liquidaciones =
        MutableStateFlow<List<Liquidacion>>(emptyList())
    val liquidaciones: StateFlow<List<Liquidacion>> =
        _liquidaciones.asStateFlow()

    private val _totalAcumulado = MutableStateFlow(0L)
    val totalAcumulado: StateFlow<Long> =
        _totalAcumulado.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun liquidar(
        estudiante: Estudiante?,
        curso: Curso?,
        tieneBeca: Boolean,
        financiaIcetex: Boolean
    ) {
        if (estudiante == null) {
            _error.value = "Primero debes registrar tu perfil."
            return
        }

        val cursoValido = cursos.find { it.id == curso?.id }

        if (cursoValido == null) {
            _error.value = "Selecciona un curso."
            return
        }

        val descuento =
            if (tieneBeca) cursoValido.costoBase / 2 else 0L

        val subtotal = cursoValido.costoBase - descuento

        val recargo =
            if (financiaIcetex) subtotal * 5 / 100 else 0L

        val liquidacion = Liquidacion(
            id = _liquidaciones.value.size + 1,
            curso = cursoValido,
            tieneBeca = tieneBeca,
            financiaIcetex = financiaIcetex,
            descuento = descuento,
            recargo = recargo,
            totalPagar = subtotal + recargo
        )

        _liquidaciones.value = _liquidaciones.value + liquidacion

        _totalAcumulado.value =
            _liquidaciones.value.sumOf { it.totalPagar }

        _error.value = null
    }
}