package project.repository

import org.jooq.generated.tables.records.UserRecord

interface UserRepo {
    fun getUser(username: String): UserRecord?
    fun insertUser(userRecord: UserRecord): UserRecord
}
