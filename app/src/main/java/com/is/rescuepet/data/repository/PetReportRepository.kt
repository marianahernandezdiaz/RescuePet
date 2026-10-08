package com.`is`.rescuepet.data.repository


import com.`is`.rescuepet.data.local.dao.PetReportDao
import com.`is`.rescuepet.data.mapper.toDomain
import com.`is`.rescuepet.data.mapper.toEntity
import com.`is`.rescuepet.domain.model.PetReport

class PetReportRepository(
    private val petReportDao: PetReportDao
) {

    suspend fun insertReport(report: PetReport) {
        petReportDao.insertReport(
            report.toEntity()
        )
    }

    suspend fun getAllReports(): List<PetReport> {
        return petReportDao.getAllReports().map { it.toDomain() }
    }

    suspend fun getReportById(id: Int): PetReport? {
        return petReportDao.getReportById(id)?.toDomain()
    }
}