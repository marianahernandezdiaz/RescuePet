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
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.`is`.rescuepet.domain.model.PetReport
import com.`is`.rescuepet.ui.viewmodel.PetReportViewModel

@Composable
fun AdminReportsScreen(
    petReportViewModel: PetReportViewModel,
    onBackClick: () -> Unit
) {

    val uiState by petReportViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        petReportViewModel.loadReports()
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
                    text = "Reportes de mascotas",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Mascotas encontradas reportadas por usuarios",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${uiState.reports.size} reportes registrados",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.reports.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "📍",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "No hay reportes registrados",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Los reportes enviados por los usuarios aparecerán aquí.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = uiState.reports,
                    key = { report -> report.id }
                ) { report ->

                    AdminReportCard(
                        report = report
                    )
                }
            }
        }
    }
}
@Composable
private fun AdminReportCard(
    report: PetReport
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

            // FOTOGRAFÍA
            if (report.imageUri != null) {

                AsyncImage(
                    model = report.imageUri,
                    contentDescription = "Fotografía de mascota encontrada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                text = "🐾 ${report.species}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = report.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "📍 Ubicación",
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = "Latitud: ${report.latitude}\nLongitud: ${report.longitude}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "📅 Fecha: ${report.date}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Estado: ${report.status}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}