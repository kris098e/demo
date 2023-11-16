package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.generated.Tables.USER
import org.jooq.generated.tables.records.UserRecord

@Singleton
class UserRepoImpl(
    private val dslContext: DSLContext,
) : UserRepo {
    override fun getUser(username: String): UserRecord? {
        return dslContext.selectFrom(USER)
            .where(USER.USERNAME.eq(username))
            .fetchOne()
    }

    override fun insertUser(userRecord: UserRecord): UserRecord {
        return dslContext.insertInto(USER)
            .set(userRecord)
            .returning()
            .fetchOne()!!
    }
}