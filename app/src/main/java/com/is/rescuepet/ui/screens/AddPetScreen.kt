package com.`is`.rescuepet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.`is`.rescuepet.ui.viewmodel.PetViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun AddPetScreen(
    petViewModel: PetViewModel,
    onPetSaved: () -> Unit,
    onBackClick: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var available by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    var imageUri by remember { mutableStateOf<Uri?>(null) }


    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {

                scope.launch {

                    try {

                        val file = File(
                            context.filesDir,
                            "pet_${System.currentTimeMillis()}.jpg"
                        )

                        context.contentResolver.openInputStream(uri)?.use { input ->

                            file.outputStream().use { output ->
                                input.copyTo(output)
                            }
                        }

                        imageUri = Uri.fromFile(file)

                    } catch (e: Exception) {

                        imageUri = null
                    }
                }
            }
        }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineLocationGranted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

            val coarseLocationGranted =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (fineLocationGranted || coarseLocationGranted) {

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

                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->

                        if (location != null) {
                            latitude = location.latitude
                            longitude = location.longitude
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        // ENCABEZADO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }

            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {

                Text(
                    text = "Registrar mascota",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Agrega una nueva mascota a RescuePet",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // CONTENIDO
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            // TARJETA DE FOTO
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                if (imageUri != null) {

                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Fotografía de la mascota",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                } else {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "🐾",
                            style = MaterialTheme.typography.displaySmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Agregar fotografía",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Toca aquí para seleccionar una imagen",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // INFORMACIÓN GENERAL
            Text(
                text = "Información general",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                placeholder = { Text("Ej. Max") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = species,
                onValueChange = { species = it },
                label = { Text("Especie") },
                placeholder = { Text("Ej. Perro") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = { Text("Raza") },
                placeholder = { Text("Ej. Labrador") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Edad") },
                    placeholder = { Text("Años") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                OutlinedTextField(
                    value = size,
                    onValueChange = { size = it },
                    label = { Text("Tamaño") },
                    placeholder = { Text("Ej. Mediano") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                placeholder = {
                    Text("Describe brevemente a la mascota...")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )

            Spacer(modifier = Modifier.height(28.dp))

            // UBICACIÓN
            Text(
                text = "Ubicación",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Coordenadas donde se encuentra la mascota.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "📍 Ubicación de la mascota",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    if (latitude != null && longitude != null) {

                        Text(
                            text = "Ubicación obtenida automáticamente",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Latitud: ${latitude}\nLongitud: ${longitude}",
                            style = MaterialTheme.typography.bodySmall
                        )

                    } else {

                        Text(
                            text = "Obteniendo ubicación...",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // DISPONIBILIDAD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Disponible para adopción",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = if (available)
                                "La mascota aparecerá en la lista de adopción."
                            else
                                "La mascota no estará disponible para adopción.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Switch(
                        checked = available,
                        onCheckedChange = { available = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // BOTÓN GUARDAR
            Button(
                onClick = {

                    petViewModel.createPet(
                        name = name,
                        species = species,
                        breed = breed,
                        age = age.toIntOrNull() ?: 0,
                        size = size,
                        description = description,
                        imageUri = imageUri?.toString(),
                        latitude = latitude ?: 0.0,
                        longitude = longitude ?: 0.0,
                        available = available
                    )

                    onPetSaved()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {

                Text(
                    text = "🐾",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "Guardar mascota",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}