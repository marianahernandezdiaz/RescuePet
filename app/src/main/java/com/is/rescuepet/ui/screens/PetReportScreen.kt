package com.`is`.rescuepet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.`is`.rescuepet.ui.viewmodel.PetReportViewModel

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.clickable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import java.io.File
import kotlinx.coroutines.launch


@Composable
fun PetReportScreen(
    petReportViewModel: PetReportViewModel,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }


    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {

                scope.launch {

                    try {

                        val file = File(
                            context.filesDir,
                            "report_${System.currentTimeMillis()}.jpg"
                        )

                        context.contentResolver.openInputStream(uri)?.use { input ->

                            file.outputStream().use { output ->
                                input.copyTo(output)
                            }
                        }

                        val savedUri = Uri.fromFile(file)

                        imageUri = savedUri

                        petReportViewModel.setImageUri(
                            savedUri.toString()
                        )

                    } catch (e: Exception) {

                        imageUri = null

                        petReportViewModel.setImageUri(null)
                    }
                }
            }
        }

    val uiState by petReportViewModel.uiState.collectAsState()
    if (uiState.success) {

        AlertDialog(
            onDismissRequest = {
                petReportViewModel.clearSuccess()
                onBackClick()
            },
            title = {
                Text("Reporte enviado")
            },
            text = {
                Text(
                    "El reporte de la mascota se envió correctamente."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        petReportViewModel.clearSuccess()
                        onBackClick()
                    }
                ) {
                    Text("Aceptar")
                }
            }

        )
    }



    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineLocation =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

            val coarseLocation =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (fineLocation || coarseLocation) {

                if (
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->

                            if (location != null) {

                                latitude = location.latitude
                                longitude = location.longitude

                                petReportViewModel.setLocation(
                                    latitude = location.latitude,
                                    longitude = location.longitude
                                )
                            }
                        }
                }
            }
        }

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    var species by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            bottom = 30.dp
        ),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ENCABEZADO
        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(
                    onClick = onBackClick
                ) {

                    Text(
                        text = "←",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Reportar mascota",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // INTRODUCCIÓN
        item {

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "¿Encontraste una mascota? 🐾",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Ayúdanos a encontrar a su familia proporcionando la siguiente información.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // INFORMACIÓN
        item {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Información de la mascota",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    // ESPECIE
                    OutlinedTextField(
                        value = species,
                        onValueChange = {
                            species = it
                            petReportViewModel.setSpecies(it)
                        },

                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Especie")
                        },
                        placeholder = {
                            Text("Ej. Perro, gato, conejo")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    // DESCRIPCIÓN
                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            petReportViewModel.setDescription(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        label = {
                            Text("Descripción")
                        },
                        placeholder = {
                            Text(
                                "Describe las características de la mascota..."
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        maxLines = 5
                    )
                }
            }
        }

        // FOTOGRAFÍA
        item {

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "Fotografía",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.10f
                        )
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        if (imageUri != null) {

                            AsyncImage(
                                model = imageUri,
                                contentDescription = "Fotografía de la mascota",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                contentScale = ContentScale.Crop
                            )

                        } else {

                            Text(
                                text = "📷",
                                style = MaterialTheme.typography.displaySmall
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Agregar fotografía",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = "Toca aquí para seleccionar una imagen",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // UBICACIÓN
        item {

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "Ubicación",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.08f
                        )
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "📍",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Column {

                            Text(
                                text = "Ubicación actual",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(
                                modifier = Modifier.height(2.dp)
                            )

                            Text(
                                text = if (latitude != null && longitude != null) {
                                    "Ubicación obtenida automáticamente"
                                } else {
                                    "Obteniendo ubicación..."
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }



        // BOTÓN
        item {


            Button(
                onClick = {
                    petReportViewModel.createReport()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {

                Text(
                    text = "Enviar reporte",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // PIE
        item {

            Text(
                text = "Gracias por ayudar a una mascota 🐾",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
