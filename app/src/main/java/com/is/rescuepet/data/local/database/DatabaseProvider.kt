package com.`is`.rescuepet.data.local.database

import android.content.Context
import androidx.room.Room
import com.`is`.rescuepet.data.local.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {

    @Volatile
    private var INSTANCE: RescuePetDatabase? = null

    fun getDatabase(context: Context): RescuePetDatabase {
        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                RescuePetDatabase::class.java,
                "rescuepet_database"
            ).build()

            INSTANCE = instance

            createAdminUser(instance)

            instance
        }
    }

    private fun createAdminUser(database: RescuePetDatabase) {

        CoroutineScope(Dispatchers.IO).launch {

            val userDao = database.userDao()

            val adminEmail = "admin@rescuepet.com"

            val existingAdmin = userDao.countUserByEmail(adminEmail)

            if (existingAdmin == 0) {

                val admin = UserEntity(
                    id = 0,
                    name = "Administrador",
                    email = adminEmail,
                    password = "123456",
                    role = "ADMIN"
                )

                userDao.insertUser(admin)
            }
        }
    }
}