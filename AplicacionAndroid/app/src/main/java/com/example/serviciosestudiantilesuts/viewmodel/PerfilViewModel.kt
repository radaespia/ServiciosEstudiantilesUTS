package com.example.serviciosestudiantilesuts.viewmodel

import androidx.lifecycle.ViewModel
import com.example.serviciosestudiantilesuts.data.Estudiante
import java.text.Normalizer
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerfilViewModel : ViewModel() {

    private val _estudiante = MutableStateFlow<Estudiante?>(null)
    val estudiante: StateFlow<Estudiante?> = _estudiante.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun guardarPerfil(
        nombre: String,
        codigo: String,
        facultad: String
    ) {
        val nombreLimpio = nombre.trim()
        val codigoLimpio = codigo.trim()

        if (nombreLimpio.isBlank() || codigoLimpio.isBlank()) {
            _error.value = "Completa el nombre y el código estudiantil."
            return
        }

        if (!codigoLimpio.all { it in '0'..'9' }) {
            _error.value = "El código estudiantil debe contener solo números."
            return
        }

        if (facultad !in listOf("Ingenierías", "Ciencias Socioeconómicas")) {
            _error.value = "Selecciona una facultad."
            return
        }

        val nombreCorreo = Normalizer
            .normalize(nombreLimpio, Normalizer.Form.NFD)
            .replace(Regex("\\p{M}+"), "")
            .lowercase(Locale.ROOT)
            .replace(Regex("[^a-z]"), "")

        if (nombreCorreo.isBlank()) {
            _error.value = "Ingresa un nombre válido."
            return
        }

        val correo = "$nombreCorreo.$codigoLimpio@uts.edu.co"

        _estudiante.value = Estudiante(
            nombre = nombreLimpio,
            codigo = codigoLimpio,
            facultad = facultad,
            correoInstitucional = correo
        )

        _error.value = null
    }
}