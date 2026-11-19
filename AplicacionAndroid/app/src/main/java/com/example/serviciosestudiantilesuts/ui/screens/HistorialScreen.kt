package com.example.serviciosestudiantilesuts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.serviciosestudiantilesuts.viewmodel.MatriculaViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HistorialScreen(
    matriculaViewModel: MatriculaViewModel,
    modifier: Modifier = Modifier
) {
    val liquidaciones by matriculaViewModel.liquidaciones
        .collectAsStateWithLifecycle()

    val totalAcumulado by matriculaViewModel.totalAcumulado
        .collectAsStateWithLifecycle()

    val formatoMoneda = remember {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO")).apply {
            maximumFractionDigits = 0
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Historial financiero",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Total acumulado a pagar",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = formatoMoneda.format(totalAcumulado),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text("Liquidaciones registradas: ${liquidaciones.size}")
            }
        }

        if (liquidaciones.isEmpty()) {
            Text("Todavía no has registrado liquidaciones.")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = liquidaciones,
                    key = { it.id }
                ) { liquidacion ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Liquidación #${liquidacion.id}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = liquidacion.curso.nombre,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                "Costo base: " +
                                        formatoMoneda.format(
                                            liquidacion.curso.costoBase
                                        )
                            )
                            Text(
                                "Beca: " +
                                        if (liquidacion.tieneBeca) "Sí" else "No"
                            )
                            Text(
                                "Descuento: " +
                                        formatoMoneda.format(liquidacion.descuento)
                            )
                            Text(
                                "Icetex: " +
                                        if (liquidacion.financiaIcetex) "Sí" else "No"
                            )
                            Text(
                                "Recargo: " +
                                        formatoMoneda.format(liquidacion.recargo)
                            )
                            HorizontalDivider()
                            Text(
                                text = "Total: " +
                                        formatoMoneda.format(liquidacion.totalPagar),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}