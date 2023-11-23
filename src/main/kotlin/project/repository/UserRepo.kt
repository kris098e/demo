package project.repository

import org.jooq.generated.tables.records.UserRecord

interface UserRepo {
    fun getUser(username: String): Pair<UserRecord, List<String>>?
    fun insertUser(userRecord: UserRecord): UserRecord
}
