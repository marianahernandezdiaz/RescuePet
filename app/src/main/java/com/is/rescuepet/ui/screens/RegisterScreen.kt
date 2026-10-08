package com.`is`.rescuepet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.`is`.rescuepet.R
import com.`is`.rescuepet.ui.viewmodel.RegisterViewModel
import com.`is`.rescuepet.ui.viewmodel.ViewModelFactory

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {

    val context = LocalContext.current

    val registerViewModel: RegisterViewModel = viewModel(
        factory = ViewModelFactory(context)
    )

    val uiState by registerViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // LOGO
        Image(
            painter = painterResource(id = R.drawable.rescuepet_logo),
            contentDescription = "Logo de RescuePet",
            modifier = Modifier
                .size(130.dp)
                .clip(RoundedCornerShape(18.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Pequeñas acciones, grandes cambios",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // TARJETA
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Crear una cuenta",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Únete a la comunidad RescuePet",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // NOMBRE
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = {
                        registerViewModel.updateName(it)
                    },
                    label = {
                        Text("Nombre")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // CORREO
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = {
                        registerViewModel.updateEmail(it)
                    },
                    label = {
                        Text("Correo electrónico")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // CONTRASEÑA
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = {
                        registerViewModel.updatePassword(it)
                    },
                    label = {
                        Text("Contraseña")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // CONFIRMAR CONTRASEÑA
                OutlinedTextField(
                    value = uiState.confirmPassword,
                    onValueChange = {
                        registerViewModel.updateConfirmPassword(it)
                    },
                    label = {
                        Text("Confirmar contraseña")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // BOTÓN CREAR CUENTA
                Button(
                    onClick = {
                        registerViewModel.register()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    enabled = !uiState.isLoading,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {

                    if (uiState.isLoading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    } else {

                        Text(
                            text = "Crear cuenta",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // VOLVER AL LOGIN
                TextButton(
                    onClick = onBackToLogin
                ) {
                    Text(
                        text = "¿Ya tienes una cuenta? Inicia sesión",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                // ERROR
                if (uiState.error != null) {

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = "Adopta • Inspira • Cambia vidas",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Medium
        )
    }
}

