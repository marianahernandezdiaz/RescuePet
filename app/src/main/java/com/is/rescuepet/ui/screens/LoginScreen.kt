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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.`is`.rescuepet.R
import com.`is`.rescuepet.ui.viewmodel.LoginViewModel
import com.`is`.rescuepet.ui.viewmodel.ViewModelFactory

@Composable
fun LoginScreen(
    onLoginSuccess: (String, Int) -> Unit,
    onRegisterClick: () -> Unit
) {

    val context = LocalContext.current

    val loginViewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(context)
    )

    val uiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.success) {
        if (uiState.success && uiState.role != null) {
            onLoginSuccess(
                uiState.role!!,
                uiState.userId!!
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // LOGO
        Image(
            painter = painterResource(id = R.drawable.rescuepet_logo),
            contentDescription = "Logo de RescuePet",
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Pequeñas acciones, grandes cambios",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // TARJETA DEL LOGIN
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
                    text = "Bienvenido de nuevo",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Inicia sesión para continuar",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                // CORREO
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = {
                        loginViewModel.updateEmail(it)
                    },
                    label = {
                        Text("Correo electrónico")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                // CONTRASEÑA
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = {
                        loginViewModel.updatePassword(it)
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
                    modifier = Modifier.height(24.dp)
                )

                // BOTÓN LOGIN
                Button(
                    onClick = {
                        loginViewModel.login()
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
                            text = "Iniciar sesión",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // REGISTRO
                TextButton(
                    onClick = onRegisterClick
                ) {
                    Text(
                        text = "¿No tienes una cuenta? Regístrate",
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
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Adopta • Inspira • Cambia vidas",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Medium
        )
    }
}