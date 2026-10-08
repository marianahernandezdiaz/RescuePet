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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.`is`.rescuepet.domain.model.AdoptionRequest
import com.`is`.rescuepet.ui.viewmodel.AdoptionRequestViewModel
import com.`is`.rescuepet.ui.theme.Cream
import com.`is`.rescuepet.ui.theme.Gold
import com.`is`.rescuepet.ui.theme.NavyBlue
import com.`is`.rescuepet.ui.theme.TextDark

@Composable
fun MyAdoptionRequestsScreen(
    userId: Int,
    adoptionRequestViewModel: AdoptionRequestViewModel,
    onBackClick: () -> Unit
) {

    val uiState by adoptionRequestViewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        adoptionRequestViewModel.loadUserRequests(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            TextButton(
                onClick = onBackClick
            ) {
                Text(
                    text = "←",
                    fontSize = 24.sp,
                    color = NavyBlue
                )
            }

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {

                Text(
                    text = "Mis solicitudes",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = NavyBlue
                )

                Text(
                    text = "Consulta el estado de tus solicitudes de adopción.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextDark
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (uiState.requests.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
            ) {

                Text(
                    text = "🐾",
                    fontSize = 45.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "No tienes solicitudes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = NavyBlue
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Cuando solicites adoptar una mascota, podrás consultar aquí su estado.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark
                )
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                items(
                    items = uiState.requests,
                    key = { request -> request.id }
                ) { request ->

                    AdoptionRequestStatusCard(
                        request = request
                    )
                }
            }
        }
    }
}

@Composable
private fun AdoptionRequestStatusCard(
    request: AdoptionRequest
) {

    val statusTitle: String
    val statusMessage: String

    when (request.status) {

        "PENDING" -> {
            statusTitle = "🟡 Solicitud pendiente"
            statusMessage =
                "Tu solicitud está siendo revisada por el equipo de RescuePet."
        }

        "APPROVED" -> {
            statusTitle = "🟢 Solicitud aprobada"
            statusMessage =
                request.adminMessage
                    ?: "Tu solicitud fue aprobada."
        }

        "REJECTED" -> {
            statusTitle = "🔴 Solicitud rechazada"
            statusMessage =
                request.adminMessage
                    ?: "Tu solicitud fue rechazada."
        }

        else -> {
            statusTitle = "Estado: ${request.status}"
            statusMessage = "Consulta nuevamente más tarde."
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                text = statusTitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = NavyBlue
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = statusMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Datos de la solicitud",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = NavyBlue
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Nombre: ${request.fullName}",
                color = TextDark
            )

            Text(
                text = "Correo: ${request.email}",
                color = TextDark
            )

            Text(
                text = "Motivo: ${request.reason}",
                color = TextDark
            )
        }
    }
}