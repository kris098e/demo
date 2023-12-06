package project.controller

import io.micronaut.core.annotation.Nullable
import io.micronaut.http.annotation.*
import project.controller.dto.ShiftsResponseDto
import project.controller.mapper.toShiftsResponseDto
import project.repository.RolesRepo
import project.repository.ShiftsRepo
import project.security.SecurityService
import project.utils.exception.exceptions.BadRequestException
import project.utils.exception.exceptions.NotFoundException
import java.time.OffsetDateTime
import java.util.UUID

@Controller("/api/shifts")
class ShiftsController(
    private val shiftsRepo: ShiftsRepo,
    private val securityService: SecurityService
) {

    @Get("/all")
    fun getAllShifts(
        @Header("Authorization") token: String,
        @QueryValue @Nullable from: OffsetDateTime? = null,
    ): List<ShiftsResponseDto> {
        securityService.verifyAuthentication(token)
        return shiftsRepo.fetchAllShifts(
            from = from ?: OffsetDateTime.now().minusYears(1),
        ).map { it.toShiftsResponseDto() }
    }

    @Get("/")
    fun getShiftsByUserId(
        @Header("Authorization") token: String,
        @QueryValue @Nullable from: OffsetDateTime? = null,
    ): List<ShiftsResponseDto> {
        val user = securityService.verifyAuthentication(token)
        return shiftsRepo.fetchShiftsByUserId(
            id = user.id,
            from = from ?: OffsetDateTime.now().minusYears(1),
        ).map { it.toShiftsResponseDto() }
    }

    @Patch("/{uuid}")
    fun updateShift(
        @Header("Authorization") token: String,
        @PathVariable uuid: String,
    ): ShiftsResponseDto {
        val user = securityService.verifyAuthentication(token)
        return shiftsRepo.updateShift(
            shiftUuid = uuid.getUuid(),
            userId = user.id,
        )?.toShiftsResponseDto() ?: throw NotFoundException("Shift not found")
    }
}

fun String.getUuid(): UUID =
    try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        throw BadRequestException("Shift not found")
    }
