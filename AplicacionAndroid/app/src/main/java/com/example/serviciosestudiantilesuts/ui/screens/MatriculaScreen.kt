package com.example.serviciosestudiantilesuts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.serviciosestudiantilesuts.viewmodel.MatriculaViewModel
import com.example.serviciosestudiantilesuts.viewmodel.PerfilViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MatriculaScreen(
    perfilViewModel: PerfilViewModel,
    matriculaViewModel: MatriculaViewModel,
    modifier: Modifier = Modifier
) {
    val estudiante by perfilViewModel.estudiante.collectAsStateWithLifecycle()
    val error by matriculaViewModel.error.collectAsStateWithLifecycle()
    val liquidaciones by matriculaViewModel.liquidaciones
        .collectAsStateWithLifecycle()

    var cursoId by rememberSaveable { mutableStateOf<Int?>(null) }
    var tieneBeca by rememberSaveable { mutableStateOf(false) }
    var financiaIcetex by rememberSaveable { mutableStateOf(false) }
    var menuAbierto by remember { mutableStateOf(false) }

    val colores = MaterialTheme.colorScheme

    val cursoSeleccionado = matriculaViewModel.cursos.find {
        it.id == cursoId
    }

    val formatoMoneda = remember {
        NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("es-CO")
        ).apply {
            maximumFractionDigits = 0
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Mi matrícula",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Explora un curso y configura tus opciones de pago.",
                style = MaterialTheme.typography.bodyMedium,
                color = colores.onSurfaceVariant
            )
        }

        if (estudiante == null) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = colores.tertiaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Primero registra tu perfil para habilitar la liquidación.",
                    modifier = Modifier.padding(16.dp),
                    color = colores.onTertiaryContainer,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

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
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = colores.primaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = colores.onPrimaryContainer,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(28.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Curso extracurricular",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Invierte en tu formación",
                            style = MaterialTheme.typography.bodySmall,
                            color = colores.onSurfaceVariant
                        )
                    }
                }

                HorizontalDivider(color = colores.outlineVariant)

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Curso",
                        style = MaterialTheme.typography.labelLarge,
                        color = colores.onSurfaceVariant
                    )

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { menuAbierto = true },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 56.dp),
                            contentPadding = PaddingValues(
                                horizontal = 14.dp,
                                vertical = 12.dp
                            )
                        ) {
                            Text(
                                text = cursoSeleccionado?.nombre
                                    ?: "Seleccionar curso",
                                modifier = Modifier.weight(1f),
                                color = colores.onSurface
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }

                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = { menuAbierto = false }
                        ) {
                            matriculaViewModel.cursos.forEach { curso ->
                                DropdownMenuItem(
                                    text = {
                                        Column(
                                            verticalArrangement =
                                                Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(curso.nombre)
                                            Text(
                                                text = formatoMoneda.format(
                                                    curso.costoBase
                                                ),
                                                style = MaterialTheme
                                                    .typography.bodySmall,
                                                color = colores.onSurfaceVariant
                                            )
                                        }
                                    },
                                    onClick = {
                                        cursoId = curso.id
                                        menuAbierto = false
                                    }
                                )
                            }
                        }
                    }
                }

                cursoSeleccionado?.let { curso ->
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = colores.primaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "VALOR DEL CURSO",
                                style = MaterialTheme.typography.labelSmall,
                                color = colores.onPrimaryContainer
                            )
                            Text(
                                text = formatoMoneda.format(curso.costoBase),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = colores.onPrimaryContainer
                            )
                        }
                    }
                }

                Text(
                    text = "Beneficios y financiación",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = colores.background,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 12.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = tieneBeca,
                            onCheckedChange = { tieneBeca = it }
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Beca de Excelencia UTS",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "50 % de descuento en el curso",
                                style = MaterialTheme.typography.bodySmall,
                                color = colores.onSurfaceVariant
                            )
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = colores.background,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Financiar con Icetex",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Recargo administrativo del 5 %",
                                style = MaterialTheme.typography.bodySmall,
                                color = colores.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = financiaIcetex,
                            onCheckedChange = { financiaIcetex = it }
                        )
                    }
                }

                error?.let { mensaje ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = colores.errorContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = mensaje,
                            modifier = Modifier.padding(12.dp),
                            color = colores.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Button(
                    enabled = estudiante != null,
                    onClick = {
                        matriculaViewModel.liquidar(
                            estudiante = estudiante,
                            curso = cursoSeleccionado,
                            tieneBeca = tieneBeca,
                            financiaIcetex = financiaIcetex
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 54.dp)
                ) {
                    Text(
                        text = "Liquidar matrícula",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Text(
                    text = "Cada liquidación se agrega a tu historial financiero.",
                    style = MaterialTheme.typography.bodySmall,
                    color = colores.onSurfaceVariant
                )
            }
        }

        liquidaciones.lastOrNull()?.let { liquidacion ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colores.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = colores.primary
                        )
                        Text(
                            text = "Última liquidación",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = liquidacion.curso.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Liquidación #${liquidacion.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = colores.onSurfaceVariant
                    )

                    HorizontalDivider(color = colores.outlineVariant)

                    DetallePagoMatricula(
                        titulo = "Costo base",
                        valor = formatoMoneda.format(
                            liquidacion.curso.costoBase
                        )
                    )

                    DetallePagoMatricula(
                        titulo = "Descuento por beca",
                        valor = "−${formatoMoneda.format(liquidacion.descuento)}"
                    )

                    DetallePagoMatricula(
                        titulo = "Recargo Icetex",
                        valor = formatoMoneda.format(liquidacion.recargo)
                    )

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = colores.primaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "TOTAL A PAGAR",
                                style = MaterialTheme.typography.labelMedium,
                                color = colores.onPrimaryContainer
                            )
                            Text(
                                text = formatoMoneda.format(
                                    liquidacion.totalPagar
                                ),
                                style = MaterialTheme.typography.headlineMedium,
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

@Composable
private fun DetallePagoMatricula(
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