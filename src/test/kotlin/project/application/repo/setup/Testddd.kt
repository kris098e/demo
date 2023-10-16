package project.application.repo.setup

import org.junit.jupiter.api.Test
import project.repository.TestRepo

class Testddd : TestConfig() {

    private val repo = TestRepo(dslContext)

    @Test
    fun test() {
        val testRepo = repo.getUsers("hans")

        println(testRepo)
    }
}