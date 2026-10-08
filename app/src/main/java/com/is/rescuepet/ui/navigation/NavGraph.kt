package com.`is`.rescuepet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.`is`.rescuepet.ui.screens.LoginScreen
import com.`is`.rescuepet.ui.screens.RegisterScreen
import com.`is`.rescuepet.ui.screens.PetsScreen
import com.`is`.rescuepet.ui.screens.AdminScreen
import com.`is`.rescuepet.ui.screens.AddPetScreen
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.`is`.rescuepet.ui.viewmodel.PetViewModel
import com.`is`.rescuepet.ui.viewmodel.ViewModelFactory
import com.`is`.rescuepet.ui.screens.PetDetailScreen
import com.`is`.rescuepet.ui.screens.PetReportScreen
import com.`is`.rescuepet.ui.screens.AdoptionRequestScreen
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.`is`.rescuepet.domain.model.Pet
import com.`is`.rescuepet.ui.viewmodel.PetReportViewModel
import com.`is`.rescuepet.ui.screens.ManagePetsScreen
import com.`is`.rescuepet.ui.screens.AdoptionRequestsAdminScreen
import com.`is`.rescuepet.ui.viewmodel.AdoptionRequestViewModel
import com.`is`.rescuepet.ui.screens.AdminReportsScreen
import com.`is`.rescuepet.ui.screens.MyAdoptionRequestsScreen

sealed class Screen(val route: String) {

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Pets : Screen("pets")

    data object Admin : Screen("admin")

    data object PetDetail : Screen("pet_detail")

    data object PetReport : Screen("pet_report")

    data object AddPet : Screen("add_pet")

    data object AdoptionRequest : Screen("adoption_request")

    data object ManagePets : Screen("manage_pets")

    data object AdoptionRequestsAdmin : Screen("adoption_requests_admin")

    data object AdminReports : Screen("admin_reports")

    data object MyAdoptionRequests : Screen("my_adoption_requests")


}

@Composable
fun RescuePetNavGraph() {

    val navController = rememberNavController()

    val currentUserId = remember {
        mutableStateOf<Int?>(null)
    }

    val selectedPet = remember {
        mutableStateOf<Pet?>(null)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        // LOGIN
        composable(Screen.Login.route) {

            LoginScreen(
                onLoginSuccess = { role, userId ->

                    currentUserId.value = userId

                    if (role == "ADMIN") {

                        navController.navigate(Screen.Admin.route)

                    } else {

                        navController.navigate(Screen.Pets.route)
                    }
                },

                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // REGISTRO
        composable(Screen.Register.route) {

            RegisterScreen(
                onRegisterSuccess = {
                    navController.popBackStack()
                },

                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // USUARIO
        composable(Screen.Pets.route) {

            val context = LocalContext.current

            val petViewModel: PetViewModel = viewModel(
                factory = ViewModelFactory(context)
            )

            PetsScreen(

                petViewModel = petViewModel,

                onPetClick = { pet ->

                    selectedPet.value = pet

                    navController.navigate(
                        Screen.PetDetail.route
                    )
                },

                onReportClick = {

                    navController.navigate(
                        Screen.PetReport.route
                    )
                },

                onMyRequestsClick = {

                    navController.navigate(
                        Screen.MyAdoptionRequests.route
                    )
                }
            )
        }

        // ADMINISTRADOR
        composable(Screen.Admin.route) {

            AdminScreen(
                onAddPetClick = {
                    navController.navigate(Screen.AddPet.route)
                },
                onManagePetsClick = {
                    navController.navigate(Screen.ManagePets.route)
                },
                onAdoptionRequestsClick = {
                    navController.navigate(
                        Screen.AdoptionRequestsAdmin.route
                    )
                },
                onReportsClick = {
                    navController.navigate(Screen.AdminReports.route)
                }
            )
        }

        composable(Screen.ManagePets.route) {

            val context = LocalContext.current

            val petViewModel: PetViewModel =
                viewModel(factory = ViewModelFactory(context))

            ManagePetsScreen(
                petViewModel = petViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // DETALLE DE MASCOTA
        composable(Screen.PetDetail.route) {

            selectedPet.value?.let { pet ->

                PetDetailScreen(

                    pet = pet,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onAdoptClick = {

                        navController.navigate(
                            Screen.AdoptionRequest.route
                        )
                    }
                )
            }
        }

        // SOLICITUD DE ADOPCIÓN
        composable(Screen.AdoptionRequest.route) {

            AdoptionRequestScreen(

                petId = selectedPet.value?.id ?: 0,

                userId = currentUserId.value ?: 0,

                onBackClick = {
                    navController.popBackStack()
                },

                onRequestSent = {
                    navController.popBackStack()
                }
            )
        }

        // REPORTE DE MASCOTA
        composable(Screen.PetReport.route) {

            val context = LocalContext.current

            val petReportViewModel: PetReportViewModel = viewModel(
                factory = ViewModelFactory(context)
            )

            PetReportScreen(
                petReportViewModel = petReportViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.AdoptionRequestsAdmin.route) {

            val context = LocalContext.current

            val adoptionRequestViewModel: AdoptionRequestViewModel =
                viewModel(
                    factory = ViewModelFactory(context)
                )

            AdoptionRequestsAdminScreen(
                adoptionRequestViewModel = adoptionRequestViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.AdminReports.route) {

            val context = LocalContext.current

            val petReportViewModel: PetReportViewModel =
                viewModel(
                    factory = ViewModelFactory(context)
                )

            AdminReportsScreen(
                petReportViewModel = petReportViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // AGREGAR MASCOTA
        composable(Screen.AddPet.route) {

            val context = LocalContext.current

            val petViewModel: PetViewModel = viewModel(
                factory = ViewModelFactory(context)
            )

            AddPetScreen(
                petViewModel = petViewModel,
                onPetSaved = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // MIS SOLICITUDES DEL USUARIO
        composable(Screen.MyAdoptionRequests.route) {

            val context = LocalContext.current

            val adoptionRequestViewModel: AdoptionRequestViewModel =
                viewModel(
                    factory = ViewModelFactory(context)
                )

            MyAdoptionRequestsScreen(
                userId = currentUserId.value ?: 0,
                adoptionRequestViewModel = adoptionRequestViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}