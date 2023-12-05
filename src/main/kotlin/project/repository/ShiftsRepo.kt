package project.repository

import project.repository.dto.ShiftDto
import java.time.OffsetDateTime
import java.util.UUID

interface ShiftsRepo {
    fun fetchAllShifts(from: OffsetDateTime): List<ShiftDto>
    fun fetchShiftsByUserId(id: Long, from: OffsetDateTime): List<ShiftDto>
    fun updateShift(shiftUuid: UUID, userId: Long,): ShiftDto?
}
