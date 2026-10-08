package com.`is`.rescuepet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.`is`.rescuepet.data.local.entity.PetReportEntity

@Dao
interface PetReportDao {

    @Insert
    suspend fun insertReport(report: PetReportEntity)

    @Query("SELECT * FROM pet_reports ORDER BY id DESC")
    suspend fun getAllReports(): List<PetReportEntity>

    @Query("SELECT * FROM pet_reports WHERE id = :id LIMIT 1")
    suspend fun getReportById(id: Int): PetReportEntity?
}