package project.controller.mapper

import org.jooq.generated.tables.records.ShowRecord
import org.jooq.generated.tables.records.UserRecord
import project.controller.dto.CreateShowDto
import project.controller.dto.CreateUserDto
import java.util.*

fun CreateUserDto.toUserRecord(uuid: UUID): UserRecord {
    val userDto = this
    return UserRecord().apply {
        this.name = userDto.name
        this.username = userDto.username
        this.password = userDto.password
        this.email = userDto.email
        this.phoneNumber = userDto.phoneNumber
        this.isSuper = userDto.isSuper
        this.uuid = uuid.toString()
        this.ticketCount = 0
        this.totalShifts = 0
    }
}

fun CreateShowDto.toShowRecord(uuid: UUID): ShowRecord {
    val showDto = this
    return ShowRecord().apply {
        this.title = showDto.title
        this.from = showDto.from
        this.to = showDto.to
        this.uuid = uuid.toString()
    }
}