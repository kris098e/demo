package project.repository.dto

import org.jooq.generated.tables.records.ShowRecord
import org.jooq.generated.tables.records.UserRecord

data class ShiftDto(
    val uuid: String,
    val userRoles: List<String>,
    val shiftRole: String,

    val user: UserRecord?,
    val show: ShowRecord,
)
