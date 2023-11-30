package project.controller

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import project.controller.dto.CreateShowDto
import project.controller.dto.ShowsResponseDto
import project.controller.mapper.toShowRecord
import project.controller.mapper.toShowResponseDto
import project.factory.UuidGenerator
import project.repository.ShowsRepo
import project.security.SecurityService

@Controller("/api/shows")
class ShowsController(
    private val showsRepo: ShowsRepo,
    private val securityService: SecurityService,
    private val uuidGenerator: UuidGenerator,
) {

    @Get("/all")
    fun getAllShows(
        @Header("Authorization") token: String,
    ): List<ShowsResponseDto> {
        securityService.verifyAuthentication(token)
        return showsRepo.fetchAllShows().map { it.toShowResponseDto() }
    }

    @Post("/create")
    fun createShow(
        @Header("Authorization") token: String,
        @Body createShowDto: CreateShowDto,
    ): ShowsResponseDto {
        securityService.verifyAuthentication(token)
        return showsRepo.createShow(
            createShowDto.toShowRecord(uuidGenerator.generateUuid())
        ).toShowResponseDto()
    }
}