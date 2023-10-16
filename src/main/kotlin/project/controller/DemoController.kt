package project.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import project.repository.TestJooq
import project.utils.exception.exceptions.NotFoundException
import project.utils.logger.debug
import project.utils.logger.info
import project.utils.logger.logger

@Controller("/demo")
class DemoController (
    val testJooq: TestJooq,
) {

    @Get(uri="/")
    fun index(): Int {
        logger.info { "DemoController.index" }
        throw NotFoundException("Not found")
        return testJooq.test()
    }

    companion object {
        private val logger = logger<DemoController>()
    }
}