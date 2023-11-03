package project.repository

import org.jooq.generated.tables.records.UserRecord

interface Repo {

    fun getUsers(username: String): List<UserRecord>

    fun insertUser(name: String): Int
}