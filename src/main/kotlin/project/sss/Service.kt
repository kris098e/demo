package project.sss

import jakarta.inject.Singleton
import project.repository.TestRepo

@Singleton
class Service(
        val repo: TestRepo,
) {

    fun insertUser(name: String): Int {
        return repo.insertUser(name)
    }
}