package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.Role
import org.jooq.generated.Tables.ROLE_TYPE
import org.jooq.generated.tables.records.UserRecord
import org.jooq.impl.DSL
import org.jooq.generated.Tables.USER_ROLE

@Singleton
class RolesRepoImpl(
    private val dslContext: DSLContext,
) : RolesRepo {
    override fun storeRoles(userId: Long, roles: List<String>) {
        return dslContext.transactionResult { configuration ->
            val context = DSL.using(configuration)
            val roles = context.selectFrom(ROLE_TYPE)
                .where(ROLE_TYPE.TYPE.`in`(roles))
                .fetch()
                .map { it.id to it.type }

            val records = roles.map { role ->
                context.newRecord(USER_ROLE).apply {
                    this.userId = userId
                    this.roleTypeId = roles.find { it.second == role.second }!!.first
                }
            }

            context.batchInsert(records).execute()
        }
    }

    override fun getRoles(userId: Long): List<String> {
        return listOf("admin")
    }
}