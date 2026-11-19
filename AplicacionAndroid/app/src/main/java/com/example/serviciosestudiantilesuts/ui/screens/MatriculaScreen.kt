package com.example.serviciosestudiantilesuts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    val cursoSeleccionado = matriculaViewModel.cursos.find {
        it.id == cursoId
    }

    val formatoMoneda = remember {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO")).apply {
            maximumFractionDigits = 0
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Matrícula extracurricular",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Selecciona un curso y las opciones de pago.")

        if (estudiante == null) {
            Text(
                text = "Registra tu perfil para habilitar la liquidación.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { menuAbierto = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    cursoSeleccionado?.nombre ?: "Seleccionar curso"
                )
            }

            DropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
            ) {
                matriculaViewModel.cursos.forEach { curso ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                "${curso.nombre} — " +
                                        formatoMoneda.format(curso.costoBase)
                            )
                        },
                        onClick = {
                            cursoId = curso.id
                            menuAbierto = false
                        }
                    )
                }
            }
        }

        cursoSeleccionado?.let { curso ->
            Text(
                text = "Costo base: ${formatoMoneda.format(curso.costoBase)}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tieneBeca,
                onCheckedChange = { tieneBeca = it }
            )
            Text(
                text = "Beca de Excelencia UTS (50 %)",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Financiar con Icetex (+5 %)",
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = financiaIcetex,
                onCheckedChange = { financiaIcetex = it }
            )
        }

        error?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error
            )
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Liquidar")
        }

        liquidaciones.lastOrNull()?.let { liquidacion ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Última liquidación registrada",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(liquidacion.curso.nombre)
                    Text(
                        "Costo base: " +
                                formatoMoneda.format(liquidacion.curso.costoBase)
                    )
                    Text(
                        "Descuento: " +
                                formatoMoneda.format(liquidacion.descuento)
                    )
                    Text(
                        "Recargo: " +
                                formatoMoneda.format(liquidacion.recargo)
                    )
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