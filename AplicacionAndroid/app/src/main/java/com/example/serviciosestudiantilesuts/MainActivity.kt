package com.example.serviciosestudiantilesuts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviciosestudiantilesuts.ui.screens.MatriculaScreen
import com.example.serviciosestudiantilesuts.ui.screens.PerfilScreen
import com.example.serviciosestudiantilesuts.ui.theme.ServiciosEstudiantilesUTSTheme
import com.example.serviciosestudiantilesuts.viewmodel.MatriculaViewModel
import com.example.serviciosestudiantilesuts.viewmodel.PerfilViewModel
import com.example.serviciosestudiantilesuts.ui.screens.HistorialScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ServiciosEstudiantilesUTSTheme {
                val perfilViewModel: PerfilViewModel = viewModel()
                val matriculaViewModel: MatriculaViewModel = viewModel()

                var seccion by rememberSaveable {
                    mutableStateOf("Perfil")
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingInterno ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingInterno)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = seccion == "Perfil",
                                onClick = { seccion = "Perfil" },
                                label = { Text("Perfil") }
                            )

                            FilterChip(
                                selected = seccion == "Matrícula",
                                onClick = { seccion = "Matrícula" },
                                label = { Text("Matrícula") }
                            )

                            FilterChip(
                                selected = seccion == "Historial",
                                onClick = { seccion = "Historial" },
                                label = { Text("Historial") }
                            )
                        }

                        when (seccion) {
                            "Perfil" -> PerfilScreen(
                                viewModel = perfilViewModel,
                                modifier = Modifier.weight(1f)
                            )

                            "Matrícula" -> MatriculaScreen(
                                perfilViewModel = perfilViewModel,
                                matriculaViewModel = matriculaViewModel,
                                modifier = Modifier.weight(1f)
                            )

                            "Historial" -> HistorialScreen(
                                matriculaViewModel = matriculaViewModel,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}