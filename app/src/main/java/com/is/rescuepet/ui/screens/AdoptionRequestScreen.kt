package com.`is`.rescuepet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.`is`.rescuepet.ui.theme.Cream
import com.`is`.rescuepet.ui.theme.Gold
import com.`is`.rescuepet.ui.theme.NavyBlue
import com.`is`.rescuepet.ui.theme.TextDark
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.`is`.rescuepet.ui.viewmodel.AdoptionRequestViewModel
import com.`is`.rescuepet.ui.viewmodel.ViewModelFactory
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AdoptionRequestScreen(
    petId: Int,
    userId: Int,
    onBackClick: () -> Unit,
    onRequestSent: () -> Unit
) {

    val context = LocalContext.current

    val viewModel: AdoptionRequestViewModel = viewModel(
        factory = ViewModelFactory(context)
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            android.util.Log.d(
                "ADOPTION_TEST",
                "UI RECIBIÓ MENSAJE: $it"
            )
        }
    }

    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    var hasPreviousPets by remember {
        mutableStateOf(false)
    }

    var housingType by remember {
        mutableStateOf("")
    }

    var petLivingPlace by remember {
        mutableStateOf("")
    }

    var reason by remember {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = Cream,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Solicitud de adopción",
                        color = Cream
                    )
                },

                navigationIcon = {
                    Button(
                        onClick = onBackClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyBlue
                        )
                    ) {
                        Text("←")
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NavyBlue
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "🐾 ¡Queremos conocerte!",
                fontSize = 24.sp,
                color = NavyBlue
            )

            Text(
                text = "Completa los siguientes datos para solicitar la adopción de esta mascota.",
                fontSize = 15.sp,
                color = TextDark
            )

            uiState.message?.let { message ->

                AlertDialog(
                    onDismissRequest = {
                        viewModel.clearMessage()
                    },

                    title = {
                        Text(
                            text = "Solicitud de adopción",
                            color = NavyBlue
                        )
                    },

                    text = {
                        Text(
                            text = message,
                            color = TextDark
                        )
                    },

                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.clearMessage()
                            }
                        ) {
                            Text(
                                text = "Aceptar",
                                color = NavyBlue
                            )
                        }
                    }
                )
            }




            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Datos personales",
                fontSize = 19.sp,
                color = NavyBlue
            )

            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Nombre completo")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = age,
                onValueChange = {
                    age = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Edad")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Teléfono")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Correo electrónico")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Dirección")
                }
            )

            OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Ciudad")
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Información sobre tu hogar",
                fontSize = 19.sp,
                color = NavyBlue
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = hasPreviousPets,
                    onCheckedChange = {
                        hasPreviousPets = it
                    }
                )

                Text(
                    text = "He tenido mascotas anteriormente",
                    color = TextDark
                )
            }

            OutlinedTextField(
                value = housingType,
                onValueChange = {
                    housingType = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Tipo de vivienda")
                },
                placeholder = {
                    Text("Casa, departamento, etc.")
                }
            )

            OutlinedTextField(
                value = petLivingPlace,
                onValueChange = {
                    petLivingPlace = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("¿Dónde vivirá la mascota?")
                },
                placeholder = {
                    Text("Interior, patio, jardín, etc.")
                }
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Motivo de adopción",
                fontSize = 19.sp,
                color = NavyBlue
            )

            OutlinedTextField(
                value = reason,
                onValueChange = {
                    reason = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                label = {
                    Text("¿Por qué deseas adoptar?")
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Button(
                onClick = {

                    android.util.Log.d(
                        "ADOPTION_TEST",
                        "BOTON ENVIAR PRESIONADO"
                    )
                    android.util.Log.d(
                        "ADOPTION_TEST",
                        "VIEWMODEL = $viewModel"
                    )

                    viewModel.createRequest(
                        userId = userId,
                        petId = petId,
                        fullName = fullName,
                        age = age.toIntOrNull() ?: 0,
                        phone = phone,
                        email = email,
                        address = address,
                        city = city,
                        hasPreviousPets = hasPreviousPets,
                        housingType = housingType,
                        petLivingPlace = petLivingPlace,
                        reason = reason
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = NavyBlue
                )
            ) {
                Text(
                    text = "Enviar solicitud",
                    fontSize = 17.sp
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}