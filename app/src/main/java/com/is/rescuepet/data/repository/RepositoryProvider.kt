package com.`is`.rescuepet.data.repository

import android.content.Context
import com.`is`.rescuepet.data.local.database.DatabaseProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object RepositoryProvider {

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepository(
            auth = FirebaseAuth.getInstance(),
            firestore = FirebaseFirestore.getInstance()
        )
    }

    fun providePetRepository(context: Context): PetRepository {
        val database = DatabaseProvider.getDatabase(context)
        return PetRepository(database.petDao())
    }

    fun providePetReportRepository(context: Context): PetReportRepository {
        val database = DatabaseProvider.getDatabase(context)
        return PetReportRepository(database.petReportDao())
    }

    fun provideAdoptionRequestRepository(
        context: Context
    ): AdoptionRequestRepository {
        val database = DatabaseProvider.getDatabase(context)

        return AdoptionRequestRepository(
            database.adoptionRequestDao()
        )
    }
}