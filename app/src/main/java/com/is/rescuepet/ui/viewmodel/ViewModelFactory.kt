package com.`is`.rescuepet.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.`is`.rescuepet.data.repository.RepositoryProvider
import com.`is`.rescuepet.ui.viewmodel.AdoptionRequestViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(
                    userRepository = RepositoryProvider.provideUserRepository(context)
                ) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(
                    userRepository = RepositoryProvider.provideUserRepository(context)
                ) as T
            }

            modelClass.isAssignableFrom(PetViewModel::class.java) -> {
                PetViewModel(
                    petRepository = RepositoryProvider.providePetRepository(context)
                ) as T
            }

            modelClass.isAssignableFrom(PetReportViewModel::class.java) -> {
                PetReportViewModel(
                    petReportRepository =
                        RepositoryProvider.providePetReportRepository(context)
                ) as T
            }
            modelClass.isAssignableFrom(AdoptionRequestViewModel::class.java) -> {
                AdoptionRequestViewModel(
                    repository = RepositoryProvider.provideAdoptionRequestRepository(context),
                    petRepository = RepositoryProvider.providePetRepository(context)
                ) as T
            }


            else -> {
                throw IllegalArgumentException(
                    "ViewModel desconocido: ${modelClass.name}"
                )
            }

        }
    }
}