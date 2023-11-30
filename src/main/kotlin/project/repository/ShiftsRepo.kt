package project.repository

import org.jooq.generated.tables.records.ShiftRecord
import project.repository.dto.ShiftDto
import java.time.OffsetDateTime

interface ShiftsRepo {
    fun fetchAllShifts(from: OffsetDateTime): List<ShiftDto>
    fun fetchShiftsByUserId(id: Long, from: OffsetDateTime): List<ShiftDto>
}