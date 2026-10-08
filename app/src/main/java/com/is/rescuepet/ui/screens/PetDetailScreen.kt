package com.`is`.rescuepet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.`is`.rescuepet.domain.model.Pet
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale


@Composable
fun PetDetailScreen(
    pet: Pet,
    onBackClick: () -> Unit,
    onAdoptClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

        contentPadding = PaddingValues(
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
                        horizontal = 20.dp,
                        vertical = 12.dp
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
                    text = "Detalles",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // IMAGEN
        item {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(240.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.10f
                    )
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
            ) {

                if (pet.imageUri != null) {

                    AsyncImage(
                        model = pet.imageUri,
                        contentDescription = "Fotografía de ${pet.name}",
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
                            text = "🐶",
                            style = MaterialTheme.typography.displayLarge
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Sin fotografía",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // NOMBRE Y RAZA
        item {

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = pet.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = pet.breed,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // INFORMACIÓN
        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                InfoCard(
                    title = "Edad",
                    value = "${pet.age} años",
                    modifier = Modifier.weight(1f)
                )

                InfoCard(
                    title = "Tamaño",
                    value = pet.size,
                    modifier = Modifier.weight(1f)
                )

                InfoCard(
                    title = "Especie",
                    value = pet.species,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // DESCRIPCIÓN
        item {

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "Sobre ${pet.name}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = pet.description,
                    style = MaterialTheme.typography.bodyLarge
                )
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
                    shape = RoundedCornerShape(16.dp),
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
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "📍",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(
                            modifier = Modifier.width(10.dp)
                        )

                        Column {

                            Text(
                                text = "Toluca, Estado de México",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(
                                modifier = Modifier.height(2.dp)
                            )

                            Text(
                                text = "Ubicación registrada de la mascota",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        // BOTÓN ADOPTAR
        item {

            Button(
                onClick = onAdoptClick,
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
                    text = "Quiero adoptar",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // PIE
        item {

            Text(
                text = "Gracias por darle una oportunidad a ${pet.name} 🐾",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.height(76.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}