package project.application.repo.setup

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import project.repository.TestJooq


@MicronautTest(environments = ["test"])
class Test : TestConfig() {

    val repository = TestJooq(dslContext)

    @org.junit.jupiter.api.Test
    fun test() {
        val count = repository.test()
        println(count)
    }
}
