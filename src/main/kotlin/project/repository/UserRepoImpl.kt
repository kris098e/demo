package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.generated.Tables.USER
import org.jooq.generated.tables.records.UserRecord
import org.jooq.impl.DSL

@Singleton
class UserRepoImpl(
    private val dslContext: DSLContext,
    private val rolesRepo: RolesRepo,
) : UserRepo {
    override fun getUser(username: String): Pair<UserRecord, List<String>>? {
        val user = dslContext.selectFrom(USER)
            .where(USER.USERNAME.eq(username))
            .fetchOne()

        return user?.let {
            val roles = rolesRepo.getRoles(it.id)
            it to roles
        }
    }

    override fun insertUser(userRecord: UserRecord): UserRecord {
        return dslContext.transactionResult { config ->
            val context = DSL.using(config)
            context.insertInto(USER)
                .set(userRecord)
                .returning()
                .fetchOne()!!
        }
    }

    override fun getAllUsers(): List<UserRecord> {
        return dslContext.selectFrom(USER).fetch()
    }
}