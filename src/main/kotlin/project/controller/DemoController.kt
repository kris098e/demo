package project.controller

import io.micronaut.http.annotation.*
import org.jooq.DSLContext
import org.jooq.generated.tables.Users
import org.jooq.generated.tables.records.UsersRecord
import project.repository.TestRepo
import project.utils.exception.exceptions.NotFoundException
import project.utils.logger.info
import project.utils.logger.logger

@Controller("/demo")
class DemoController (
        val testRepo: TestRepo,
        val dslContext: DSLContext
) {

    @Get(uri="/hej/{name}")
    fun index(@PathVariable name: String): String {
        logger.info { "DemoController.index" }
        val users = testRepo.getUsers(name)
        if (users.isEmpty()) {
            throw NotFoundException("User not found")
        }
        return users.first().username
    }

    @Post(uri="/hej")
    fun sss(@Body person: Person,): Hej {
        val test = testRepo.insertUser(person.name)
        return Hej("hej ${person.name} $test")
    }

    companion object {
        private val logger = logger<DemoController>()
    }
}