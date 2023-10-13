package project.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import project.repository.TestJooq
import project.utils.logger.logger

@Controller("/demo")
class DemoController (
    val testJooq: TestJooq,
) {

    @Get(uri="/")
    fun index(): Int {
        return testJooq.test()
    }

    companion object {
        private val logger = logger<DemoController>()
    }
}