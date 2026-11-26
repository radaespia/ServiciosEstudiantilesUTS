package com.example.serviciosestudiantilesuts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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

    val colores = MaterialTheme.colorScheme

    val formatoMoneda = remember {
        NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("es-CO")
        ).apply {
            maximumFractionDigits = 0
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Mi historial",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Consulta tus liquidaciones y el total acumulado.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colores.onSurfaceVariant
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colores.primary,
                    contentColor = colores.onPrimary
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "TOTAL ACUMULADO A PAGAR",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = formatoMoneda.format(totalAcumulado),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider(
                        color = colores.onPrimary.copy(alpha = 0.2f)
                    )

                    Text(
                        text = "Liquidaciones registradas: ${liquidaciones.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        if (liquidaciones.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colores.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = colores.primaryContainer
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null,
                                tint = colores.onPrimaryContainer,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(32.dp)
                            )
                        }

                        Text(
                            text = "Tu historial comienza aquí",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Cuando liquides un curso en Matrícula, " +
                                    "podrás consultar su detalle en esta sección.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colores.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style
                                .TextAlign.Center
                        )
                    }
                }
            }
        } else {
            item {
                Text(
                    text = "Liquidaciones registradas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(
                items = liquidaciones,
                key = { it.id }
            ) { liquidacion ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colores.surface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = colores.primaryContainer
                        ) {
                            Text(
                                text = "Liquidación #${liquidacion.id}",
                                modifier = Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 6.dp
                                ),
                                style = MaterialTheme.typography.labelMedium,
                                color = colores.onPrimaryContainer
                            )
                        }

                        Text(
                            text = liquidacion.curso.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Beca: " +
                                    (if (liquidacion.tieneBeca) "Sí" else "No") +
                                    " · Icetex: " +
                                    (if (liquidacion.financiaIcetex) "Sí" else "No"),
                            style = MaterialTheme.typography.bodySmall,
                            color = colores.onSurfaceVariant
                        )

                        HorizontalDivider(
                            color = colores.outlineVariant
                        )

                        DetalleHistorial(
                            titulo = "Costo base",
                            valor = formatoMoneda.format(
                                liquidacion.curso.costoBase
                            )
                        )

                        DetalleHistorial(
                            titulo = "Descuento por beca",
                            valor = "−" + formatoMoneda.format(
                                liquidacion.descuento
                            )
                        )

                        DetalleHistorial(
                            titulo = "Recargo Icetex",
                            valor = formatoMoneda.format(
                                liquidacion.recargo
                            )
                        )

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            color = colores.primaryContainer
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "TOTAL DE ESTA MATRÍCULA",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colores.onPrimaryContainer
                                )
                                Text(
                                    text = formatoMoneda.format(
                                        liquidacion.totalPagar
                                    ),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = colores.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetalleHistorial(
    titulo: String,
    valor: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = titulo,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}