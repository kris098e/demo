package project.application.repo.setup

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.jooq.generated.tables.records.UsersRecord
import org.junit.jupiter.api.Test

@MicronautTest
class Testddd {


    @Inject
    lateinit var repo: TestRepo

    @Test
    fun test() {
        repo.dslContext.insertInto(org.jooq.generated.tables.Users.USERS).set(UsersRecord().apply {
            this.username = "hans"
            this.password = "password"
            this.enabled = true
        }).execute()

        val testRepo = repo.getUsers("hans")

        println(testRepo.size)
    }
}