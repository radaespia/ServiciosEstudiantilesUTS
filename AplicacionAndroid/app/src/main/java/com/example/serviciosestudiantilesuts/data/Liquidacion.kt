package com.example.serviciosestudiantilesuts.data

data class Liquidacion(
    val id: Int,
    val curso: Curso,
    val tieneBeca: Boolean,
    val financiaIcetex: Boolean,
    val descuento: Long,
    val recargo: Long,
    val totalPagar: Long
)