package project.repository

import org.jooq.generated.tables.records.ShiftRecord
import project.repository.dto.ShiftDto

interface ShiftsRepo {
    fun fetchAllShifts(): List<ShiftDto>
    fun fetchShiftsByUserId(id: Long): List<ShiftDto>
}