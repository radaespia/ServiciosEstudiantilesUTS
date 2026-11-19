# Sistema Integrado de Servicios Estudiantiles UTS

Proyecto académico de la materia Aplicaciones Móviles de las
Unidades Tecnológicas de Santander.

## Integrantes

- Julian Andres Rada Navarro
- Wilson Steven Figueroa

## Descripción

Aplicación Android nativa que simula un portal de autoservicio
estudiantil con tres módulos: perfil académico, liquidación de
matrícula extracurricular e historial financiero.

## Funcionalidades

### Perfil académico
- Registro del nombre, código estudiantil y facultad.
- Validación de campos obligatorios y código numérico.
- Generación automática de un correo institucional simulado.

### Matrícula extracurricular
- Selección de cursos con distintos costos base.
- Beca de Excelencia UTS: descuento del 50 %.
- Financiación con Icetex: recargo del 5 % sobre el valor
  después del descuento.
- Botón Liquidar deshabilitado mientras no exista un perfil.

### Historial financiero
- Lista de liquidaciones registradas mediante LazyColumn.
- Detalle de costos, descuentos, recargos y total.
- Total acumulado actualizado por el ViewModel.

## Tecnologías

- Kotlin.
- Jetpack Compose.
- Material Design 3.
- Arquitectura MVVM.
- MutableStateFlow privado y StateFlow público.
- collectAsStateWithLifecycle para observar los estados.
- Gradle con Kotlin DSL.
- Git y GitHub.

## Organización del código

El código principal está en:
AplicacionAndroid/app/src/main/java/com/example/serviciosestudiantilesuts/

- data: modelos Estudiante, Curso y Liquidacion.
- viewmodel: validaciones, generación del correo, cálculos y estados.
- ui/screens: pantallas de perfil, matrícula e historial.
- ui/theme: colores, tipografía y tema visual.
- MainActivity.kt: inicio de la aplicación y navegación entre secciones.

## Cómo ejecutar

1. Clonar este repositorio.
2. Abrir Android Studio.
3. Seleccionar Open y abrir la carpeta AplicacionAndroid.
4. Esperar la sincronización de Gradle.
5. Seleccionar un emulador o dispositivo con Android 9
   (API 28) o superior.
6. Ejecutar la configuración app.

## Cursos y valores de ejemplo

| Curso | Costo base |
|---|---:|
| Inglés Técnico | $200.000 COP |
| Programación Web | $300.000 COP |
| Ciberseguridad | $400.000 COP |

Ejemplo: Inglés Técnico con beca e Icetex:
$200.000 - $100.000 + $5.000 = $105.000 COP.

## Alcance

La aplicación es una simulación académica y no se conecta
con los sistemas oficiales de las UTS ni de Icetex.

El perfil y las liquidaciones se mantienen en memoria.
No se almacenan permanentemente al cerrar la aplicación.
