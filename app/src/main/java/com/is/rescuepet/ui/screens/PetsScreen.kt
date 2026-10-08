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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.`is`.rescuepet.domain.model.Pet
import com.`is`.rescuepet.ui.viewmodel.PetViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class DemoPet(
    val name: String,
    val species: String,
    val breed: String,
    val age: String,
    val size: String,
    val emoji: String
)
@Composable
fun PetsScreen(
    petViewModel: PetViewModel,
    onPetClick: (Pet) -> Unit,
    onReportClick: () -> Unit,
    onMyRequestsClick: () -> Unit
){

    val uiState by petViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        petViewModel.loadPets()
    }
    var searchQuery by remember { mutableStateOf("") }



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
                        top = 20.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "RescuePet",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = "Encuentra a tu nuevo amigo 🐾",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Card(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(50),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.15f
                        )
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    )
                ) {

                    Text(
                        text = "👤",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

        // SALUDO
        item {

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "Hola 👋",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "Tal vez tu nuevo mejor amigo está aquí.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // BUSCADOR
        item {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    petViewModel.searchPets(query)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                placeholder = {
                    Text("Buscar mascota...")
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )
        }

        // REPORTE
        item {

            OutlinedButton(
                onClick = onReportClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(46.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {

                Text(
                    text = "🐾 Reportar mascota encontrada",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        item {

            OutlinedButton(
                onClick = onMyRequestsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(46.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {

                Text(
                    text = "📋 Mis solicitudes",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // TÍTULO
        item {

            Text(
                text = "Mascotas disponibles",
                modifier = Modifier.padding(horizontal = 20.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // MASCOTAS
        items(uiState.pets) { pet ->

            PetCard(
                pet = pet,
                onPetClick = onPetClick
            )
        }

        // PIE
        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 10.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Adopta • Inspira • Cambia vidas 🐾",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    emoji: String,
    text: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.height(62.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = emoji,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.width(5.dp)
            )

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun PetCard(
    pet: Pet,
    onPetClick: (Pet) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column {

            // IMAGEN
// IMAGEN
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.08f
                    )
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
                            text = "🐾",
                            style = MaterialTheme.typography.displaySmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Sin fotografía",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // INFORMACIÓN
            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = pet.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "♡",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "${pet.breed} • ${pet.age}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "${pet.species} • ${pet.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                TextButton(
                    onClick = {
                        onPetClick(pet)
                    }

                ) {

                    Text(
                        text = "Ver detalles →",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

