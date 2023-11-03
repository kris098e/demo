package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.generated.tables.User
import org.jooq.generated.tables.records.UserRecord
import project.utils.logger.info
import project.utils.logger.logger
import kotlin.math.log

@Singleton
open class TestRepo (
        val dslContext: DSLContext,
) : Repo {

    override fun getUsers(username: String): List<UserRecord> {
        logger.info { "TestRepo.getUsers" }

        return dslContext.selectFrom(User.USER).where(User.USER.USERNAME.eq(username)).fetch()
    }

    override fun insertUser(name: String): Int {
        logger.info { "TestRepo.insertUser" }
        val userRecord = UserRecord().apply {
            this.username = name
            this.password = "password"
            this.enabled = true
        }

        return dslContext.insertInto(Users.USERS).set(userRecord).execute()
    }

    companion object {
        private val logger = logger<TestRepo>()
    }
}