package project.repository

import org.jooq.generated.tables.records.UsersRecord

interface Repo {

    fun getUsers(username: String): List<UsersRecord>

    fun insertUser(name: String): Int
}