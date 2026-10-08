package com.`is`.rescuepet.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.`is`.rescuepet.domain.model.User
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): User {

        // 1. Crear usuario en Firebase Authentication
        val result = auth.createUserWithEmailAndPassword(
            email,
            password
        ).await()

        val uid = result.user?.uid
            ?: throw Exception("No se pudo obtener el UID del usuario")

        // 2. Crear información adicional en Firestore
        val user = User(
            id = 0,
            uid = uid,
            name = name,
            email = email,
            role = "USER"
        )

        firestore
            .collection("users")
            .document(uid)
            .set(
                mapOf(
                    "uid" to user.uid,
                    "name" to user.name,
                    "email" to user.email,
                    "role" to user.role
                )
            )
            .await()

        return user
    }

    suspend fun login(
        email: String,
        password: String
    ): User {

        // Iniciar sesión en Firebase Authentication
        val result = auth
            .signInWithEmailAndPassword(email, password)
            .await()

        val firebaseUser = result.user
            ?: throw Exception("No se pudo obtener el usuario")

        // Obtener datos adicionales desde Firestore
        val document = firestore
            .collection("users")
            .document(firebaseUser.uid)
            .get()
            .await()

        val role = document.getString("role") ?: "USER"
        val name = document.getString("name") ?: ""

        return User(
            id = 0,
            uid = firebaseUser.uid,
            name = name,
            email = firebaseUser.email ?: email,
            role = role
        )
    }

    suspend fun getCurrentUser(): User? {

        val firebaseUser = auth.currentUser ?: return null

        val document = firestore
            .collection("users")
            .document(firebaseUser.uid)
            .get()
            .await()

        if (!document.exists()) return null

        return User(
            id = 0,
            uid = firebaseUser.uid,
            name = document.getString("name") ?: "",
            email = document.getString("email")
                ?: firebaseUser.email
                ?: "",
            role = document.getString("role") ?: "USER"
        )
    }

    fun logout() {
        auth.signOut()
    }
}