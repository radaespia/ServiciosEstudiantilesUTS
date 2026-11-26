package com.example.serviciosestudiantilesuts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.serviciosestudiantilesuts.ui.screens.ServiciosApp
import com.example.serviciosestudiantilesuts.ui.theme.ServiciosEstudiantilesUTSTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ServiciosEstudiantilesUTSTheme {
                ServiciosApp()
            }
        }
    }
}