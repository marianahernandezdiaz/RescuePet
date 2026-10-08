package com.`is`.rescuepet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.`is`.rescuepet.domain.model.Pet
import com.`is`.rescuepet.ui.viewmodel.PetViewModel

@Composable
fun ManagePetsScreen(
    petViewModel: PetViewModel,
    onBackClick: () -> Unit
) {

    val uiState by petViewModel.uiState.collectAsState()

    var searchQuery by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        petViewModel.loadPets()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // ENCABEZADO
        Row(
            modifier = Modifier.fillMaxWidth(),
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

            Column {

                Text(
                    text = "Gestionar mascotas",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Mascotas registradas en RescuePet",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BUSCADOR
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->

                searchQuery = query

                petViewModel.searchPets(query)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Buscar mascota")
            },
            placeholder = {
                Text("Nombre o raza")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${uiState.pets.size} mascotas registradas",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // LISTA
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                items = uiState.pets,
                key = { pet -> pet.id }
            ) { pet ->

                AdminPetCard(
                    pet = pet,
                    onAvailabilityChange = { available ->

                        petViewModel.updatePetAvailability(
                            petId = pet.id,
                            available = available
                        )
                    }
                )
            }
        }
    }
    @Composable
    fun AdminPetCard(
        pet: Pet,
        onAvailabilityChange: (Boolean) -> Unit
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = pet.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = "${pet.species} • ${pet.breed}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Edad: ${pet.age} años • Tamaño: ${pet.size}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text(
                        text = if (pet.available) "🟢" else "🔴",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = if (pet.available)
                            "Disponible para adopción"
                        else
                            "No disponible / Adoptada",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Switch(
                        checked = pet.available,
                        onCheckedChange = onAvailabilityChange
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminPetCard(
    pet: Pet,
    onAvailabilityChange: (Boolean) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = pet.name,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "${pet.species} • ${pet.breed}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Edad: ${pet.age} años • Tamaño: ${pet.size}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    text = if (pet.available) "🟢" else "🔴",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = if (pet.available)
                        "Disponible para adopción"
                    else
                        "No disponible / Adoptada",
                    style = MaterialTheme.typography.bodyMedium
                )

                Switch(
                    checked = pet.available,
                    onCheckedChange = onAvailabilityChange
                )
            }
        }
    }
}