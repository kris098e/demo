package project.controller

import io.micronaut.http.annotation.*
import org.jooq.DSLContext
import project.repository.Repo
import project.security.JwtService
import project.security.dto.AuthenticatedUser
import project.utils.exception.exceptions.NotFoundException
import project.utils.logger.info
import project.utils.logger.logger

@Controller("/demo")
class DemoController (
        val dslContext: DSLContext,
        val jwtService: JwtService,
) {

    @Get(uri="/hej/{name}")
    fun index(@PathVariable name: String): String {
        return jwtService.generateJwt(userId = "name", username = name, password = "hej")
    }

    @Get(uri = "/jwtparse")
    fun dddd(@Header("Authorization") header: String): AuthenticatedUser {
        return jwtService.parseJwtIntoUser(header)
    }

    companion object {
        private val logger = logger<DemoController>()
    }
}