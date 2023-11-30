package project.controller

import io.micronaut.core.annotation.Nullable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.QueryValue
import project.controller.dto.ShiftsResponseDto
import project.controller.mapper.toShiftsResponseDto
import project.repository.ShiftsRepo
import project.security.SecurityService
import java.time.OffsetDateTime

@Controller("/api/shifts")
class ShiftsController(
    private val shiftsRepo: ShiftsRepo,
    private val securityService: SecurityService
) {

    @Get("/all/{from}")
    fun getAllShifts(
        @Header("Authorization") token: String,
        @QueryValue @Nullable from: OffsetDateTime? = null,
    ): List<ShiftsResponseDto> {
        securityService.verifyAuthentication(token)
        return shiftsRepo.fetchAllShifts(
            from = from ?: OffsetDateTime.now().minusYears(1),
        ).map { it.toShiftsResponseDto() }
    }

    @Get("/{from}")
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
}
