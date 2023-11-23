package project.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import project.controller.dto.ShiftsResponseDto
import project.controller.mapper.toShiftsResponseDto
import project.repository.ShiftsRepo
import project.security.SecurityService

@Controller("/api/shifts")
class ShiftsController(
    private val shiftsRepo: ShiftsRepo,
    private val securityService: SecurityService
) {

    @Get("/all")
    fun getAllShifts(
        @Header("Authorization") token: String,
    ): List<ShiftsResponseDto> {
        securityService.verifyAuthentication(token)
        return shiftsRepo.fetchAllShifts().map { it.toShiftsResponseDto() }
    }

    @Get("/")
    fun getShiftsByUserId(
        @Header("Authorization") token: String,
    ): String {
        val user = securityService.verifyAuthentication(token)
        return shiftsRepo.fetchShiftsByUserId(user.id).toString()
    }
}