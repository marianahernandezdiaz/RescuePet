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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminScreen(
    onAddPetClick: () -> Unit,
    onManagePetsClick: () -> Unit,
    onAdoptionRequestsClick: () -> Unit,
    onReportsClick: () -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ENCABEZADO
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp
                )
        ) {

            Text(
                text = "🐾 RescuePet",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Panel de administrador",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // CONTENIDO
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                top = 24.dp,
                bottom = 30.dp
            )
        ) {

            // BIENVENIDA
            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Bienvenido, administrador 👋",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Gestiona las mascotas, solicitudes y reportes de RescuePet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            // TÍTULO
            item {

                Text(
                    text = "Gestión de RescuePet",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // OPCIONES PRINCIPALES
            item {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    AdminOptionCard(
                        icon = "🐾",
                        title = "Mascotas",
                        description = "Gestionar mascotas",
                        modifier = Modifier.weight(1f),
                        onClick = onManagePetsClick
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    AdminOptionCard(
                        icon = "📋",
                        title = "Solicitudes",
                        description = "Revisar adopciones",
                        modifier = Modifier.weight(1f),
                        onClick = onAdoptionRequestsClick
                    )
                }
            }

            item {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    AdminOptionCard(
                        icon = "➕",
                        title = "Agregar",
                        description = "Nueva mascota",
                        modifier = Modifier.weight(1f),
                        onClick = onAddPetClick
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    AdminOptionCard(
                        icon = "📍",
                        title = "Reportes",
                        description = "Animales encontrados",
                        modifier = Modifier.weight(1f),
                        onClick = onReportsClick
                    )
                }
            }

            // ACCIÓN RÁPIDA
            item {

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Acciones rápidas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAddPetClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(
                        text = "＋ Registrar nueva mascota",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AdminOptionCard(
    icon: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        onClick = onClick
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}