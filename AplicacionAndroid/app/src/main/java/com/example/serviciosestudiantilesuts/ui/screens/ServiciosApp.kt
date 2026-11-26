package com.example.serviciosestudiantilesuts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviciosestudiantilesuts.viewmodel.MatriculaViewModel
import com.example.serviciosestudiantilesuts.viewmodel.PerfilViewModel

@Composable
fun ServiciosApp() {
    val perfilViewModel: PerfilViewModel = viewModel()
    val matriculaViewModel: MatriculaViewModel = viewModel()

    var seccion by rememberSaveable { mutableStateOf(0) }

    val estadosPantallas = rememberSaveableStateHolder()
    val colores = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colores.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colores.primary)
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = colores.tertiaryContainer
                    ) {
                        Text(
                            text = "UTS",
                            modifier = Modifier.padding(12.dp),
                            color = colores.onTertiaryContainer,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Column {
                        Text(
                            text = "Servicios UTS",
                            color = colores.onPrimary,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Tu espacio estudiantil",
                            color = colores.onPrimary.copy(alpha = 0.85f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = colores.surface,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = seccion == 0,
                    onClick = { seccion = 0 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    },
                    label = { Text("Perfil") }
                )

                NavigationBarItem(
                    selected = seccion == 1,
                    onClick = { seccion = 1 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null
                        )
                    },
                    label = { Text("Matrícula") }
                )

                NavigationBarItem(
                    selected = seccion == 2,
                    onClick = { seccion = 2 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null
                        )
                    },
                    label = { Text("Historial") }
                )
            }
        }
    ) { paddingInterno ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .imePadding()
        ) {
            estadosPantallas.SaveableStateProvider(key = seccion) {
                when (seccion) {
                    0 -> PerfilScreen(
                        viewModel = perfilViewModel
                    )

                    1 -> MatriculaScreen(
                        perfilViewModel = perfilViewModel,
                        matriculaViewModel = matriculaViewModel
                    )

                    2 -> HistorialScreen(
                        matriculaViewModel = matriculaViewModel
                    )
                }
            }
        }
    }
}