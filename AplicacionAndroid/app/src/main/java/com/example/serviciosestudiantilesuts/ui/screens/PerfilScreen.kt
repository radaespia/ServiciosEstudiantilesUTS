package com.example.serviciosestudiantilesuts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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

    val facultades = listOf(
        "Ingenierías",
        "Ciencias Socioeconómicas"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Perfil académico",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Registra tus datos para acceder a los servicios UTS.",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código estudiantil") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { menuAbierto = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = facultad.ifBlank {
                        "Seleccionar facultad"
                    }
                )
            }

            DropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
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

        error?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = {
                viewModel.guardarPerfil(
                    nombre = nombre,
                    codigo = codigo,
                    facultad = facultad
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar perfil")
        }

        estudiante?.let { perfil ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Perfil registrado",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text("Nombre: ${perfil.nombre}")
                    Text("Código: ${perfil.codigo}")
                    Text("Facultad: ${perfil.facultad}")
                    Text("Correo: ${perfil.correoInstitucional}")
                }
            }
        }
    }
}