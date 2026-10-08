package com.`is`.rescuepet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.`is`.rescuepet.ui.viewmodel.AdoptionRequestViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun AdoptionRequestsAdminScreen(
    adoptionRequestViewModel: AdoptionRequestViewModel,
    onBackClick: () -> Unit
) {

    val uiState by adoptionRequestViewModel.uiState.collectAsState()

    var requestToReject by remember {
        mutableStateOf<com.`is`.rescuepet.domain.model.AdoptionRequest?>(null)
    }

    var rejectionMessage by remember {
        mutableStateOf("")
    }


    LaunchedEffect(Unit) {
        adoptionRequestViewModel.loadPendingRequests()
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
                    text = "Solicitudes de adopción",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Solicitudes pendientes de revisión",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${uiState.requests.size} solicitudes pendientes",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.requests.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "📋",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "No hay solicitudes pendientes",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Las nuevas solicitudes aparecerán aquí.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = uiState.requests,
                    key = { request -> request.id }
                ) { request ->

                    AdoptionRequestAdminCard(
                        request = request,
                        onApprove = {
                            adoptionRequestViewModel.approveRequest(request)
                        },
                        onReject = {
                            requestToReject = request
                            rejectionMessage = ""
                        }
                    )
                }
            }
        }
    }
    if (requestToReject != null) {

        AlertDialog(
            onDismissRequest = {
                requestToReject = null
                rejectionMessage = ""
            },
            title = {
                Text("Rechazar solicitud")
            },
            text = {

                OutlinedTextField(
                    value = rejectionMessage,
                    onValueChange = {
                        rejectionMessage = it
                    },
                    label = {
                        Text("Motivo del rechazo")
                    },
                    placeholder = {
                        Text("Escribe el motivo...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        val request = requestToReject

                        if (request != null && rejectionMessage.isNotBlank()) {

                            adoptionRequestViewModel.rejectRequest(
                                request = request,
                                adminMessage = rejectionMessage
                            )

                            requestToReject = null
                            rejectionMessage = ""
                        }
                    }
                ) {
                    Text("Rechazar")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        requestToReject = null
                        rejectionMessage = ""
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun AdoptionRequestAdminCard(
    request: com.`is`.rescuepet.domain.model.AdoptionRequest,
    onApprove: () -> Unit,
    onReject: () -> Unit
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

            Text(
                text = "Solicitud #${request.id}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "👤 ${request.fullName}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "📧 ${request.email}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "📱 ${request.phone}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "🏠 ${request.city}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Tipo de vivienda: ${request.housingType}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Lugar para la mascota: ${request.petLivingPlace}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Motivo:",
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = request.reason,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = onReject,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Rechazar")
                }

                Button(
                    onClick = onApprove,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Aprobar")
                }
            }
        }
    }
}