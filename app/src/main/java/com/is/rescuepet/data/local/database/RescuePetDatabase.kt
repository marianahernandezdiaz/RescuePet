package com.`is`.rescuepet.data.local.database



import androidx.room.Database
import androidx.room.RoomDatabase
import com.`is`.rescuepet.data.local.dao.PetDao
import com.`is`.rescuepet.data.local.dao.PetReportDao
import com.`is`.rescuepet.data.local.dao.UserDao
import com.`is`.rescuepet.data.local.entity.PetEntity
import com.`is`.rescuepet.data.local.entity.PetReportEntity
import com.`is`.rescuepet.data.local.entity.UserEntity
import com.`is`.rescuepet.data.local.dao.AdoptionRequestDao
import com.`is`.rescuepet.data.local.entity.AdoptionRequestEntity

@Database(
    entities = [
        UserEntity::class,
        PetEntity::class,
        PetReportEntity::class,
        AdoptionRequestEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class RescuePetDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun petDao(): PetDao

    abstract fun petReportDao(): PetReportDao

    abstract fun adoptionRequestDao(): AdoptionRequestDao
}