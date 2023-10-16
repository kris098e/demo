package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.generated.tables.Users
import org.jooq.generated.tables.records.UsersRecord
import org.jooq.impl.DefaultDSLContext
import project.utils.logger.info
import project.utils.logger.logger
import kotlin.math.log

@Singleton
class TestRepo (
        val dslContext: DSLContext,
) {

    fun getUsers(username: String): List<UsersRecord> {
        logger.info { "TestRepo.getUsers" }

        return dslContext.selectFrom(Users.USERS).where(Users.USERS.USERNAME.eq(username)).fetch()
    }

    fun insertUser(name: String): Int {
        logger.info { "TestRepo.insertUser" }
        val userRecord = UsersRecord().apply {
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