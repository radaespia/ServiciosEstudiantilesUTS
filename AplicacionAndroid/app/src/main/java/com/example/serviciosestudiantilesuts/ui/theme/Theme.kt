package com.example.serviciosestudiantilesuts.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = VerdeUTS,
    onPrimary = Color.White,
    primaryContainer = VerdeSuave,
    onPrimaryContainer = VerdeOscuro,

    secondary = VerdeOscuro,
    onSecondary = Color.White,
    secondaryContainer = VerdeSuave,
    onSecondaryContainer = VerdeOscuro,

    tertiary = Color(0xFF735600),
    onTertiary = Color.White,
    tertiaryContainer = DoradoSuave,
    onTertiaryContainer = Color(0xFF302200),

    background = FondoClaro,
    onBackground = TextoPrincipal,
    surface = BlancoTarjeta,
    onSurface = TextoPrincipal,
    surfaceVariant = Color(0xFFE7EEE9),
    onSurfaceVariant = TextoSecundario,
    outline = Color(0xFF718278),
    outlineVariant = Color(0xFFC5D2C9),

    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9DD5B7),
    onPrimary = VerdeOscuro,
    primaryContainer = VerdeUTS,
    onPrimaryContainer = VerdeSuave,

    secondary = Color(0xFFB2CCBC),
    onSecondary = Color(0xFF203629),
    secondaryContainer = Color(0xFF364D3F),
    onSecondaryContainer = VerdeSuave,

    tertiary = Color(0xFFE8C46C),
    onTertiary = Color(0xFF3E2E00),
    tertiaryContainer = Color(0xFF594300),
    onTertiaryContainer = DoradoSuave,

    background = Color(0xFF101813),
    onBackground = Color(0xFFE0E9E1),
    surface = Color(0xFF17211B),
    onSurface = Color(0xFFE0E9E1),
    surfaceVariant = Color(0xFF35453B),
    onSurfaceVariant = Color(0xFFBDCCC1),
    outline = Color(0xFF88998D),
    outlineVariant = Color(0xFF3F5045),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun ServiciosEstudiantilesUTSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        },
        typography = Typography,
        content = content
    )
}