package com.example.serviciosestudiantilesuts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.serviciosestudiantilesuts.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel,
    modifier: Modifier = Modifier
) {
    val estudiante by viewModel.estudiante.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    var nombre by rememberSaveable { mutableStateOf("") }
    var codigo by rememberSaveable { mutableStateOf("") }
    var facultad by rememberSaveable { mutableStateOf("") }
    var menuAbierto by remember { mutableStateOf(false) }

    val colores = MaterialTheme.colorScheme
    val facultades = listOf(
        "Ingenierías",
        "Ciencias Socioeconómicas"
    )

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
                text = "Mi perfil",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Tu primer paso para acceder a los servicios UTS.",
                style = MaterialTheme.typography.bodyMedium,
                color = colores.onSurfaceVariant
            )
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
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = colores.onPrimaryContainer,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(28.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Información académica",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Completa tus datos",
                            style = MaterialTheme.typography.bodySmall,
                            color = colores.onSurfaceVariant
                        )
                    }
                }

                HorizontalDivider(
                    color = colores.outlineVariant
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    placeholder = { Text("Ej. Julián Rada") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código estudiantil") },
                    placeholder = { Text("Ingresa tu código") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Facultad",
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
                                text = facultad.ifBlank {
                                    "Seleccionar facultad"
                                },
                                modifier = Modifier.weight(1f),
                                color = colores.onSurface
                            )

                            Icon(
                                imageVector =
                                    Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }

                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = {
                                menuAbierto = false
                            }
                        ) {
                            facultades.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        facultad = opcion
                                        menuAbierto = false
                                    }
                                )
                            }
                        }
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
                    onClick = {
                        viewModel.guardarPerfil(
                            nombre = nombre,
                            codigo = codigo,
                            facultad = facultad
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 54.dp)
                ) {
                    Text(
                        text = "Guardar perfil",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Text(
                    text = "Al guardar, generaremos tu correo institucional simulado.",
                    style = MaterialTheme.typography.bodySmall,
                    color = colores.onSurfaceVariant
                )
            }
        }

        estudiante?.let { perfil ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colores.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = colores.onPrimaryContainer
                        )

                        Text(
                            text = "Perfil registrado",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = perfil.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Código: ${perfil.codigo}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = perfil.facultad,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    HorizontalDivider(
                        color = colores.onPrimaryContainer.copy(
                            alpha = 0.15f
                        )
                    )

                    Text(
                        text = "CORREO INSTITUCIONAL",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = perfil.correoInstitucional,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}