package project.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import project.controller.dto.ShowsResponseDto
import project.controller.mapper.toShowResponseDto
import project.repository.ShowsRepo
import project.security.SecurityService

@Controller("/api/shows")
class ShowsController(
    private val showsRepo: ShowsRepo,
    private val securityService: SecurityService,
) {

    @Get("/all")
    fun getAllShows(
        @Header("Authorization") token: String,
    ): List<ShowsResponseDto> {
        securityService.verifyAuthentication(token)
        return showsRepo.fetchAllShows().map { it.toShowResponseDto() }
    }
}