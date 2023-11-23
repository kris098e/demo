package project.controller.mapper

import org.jooq.generated.tables.records.UserRecord
import project.controller.dto.CreaseUserResponseDto
import java.util.*

fun UserRecord.toUserResponseDto(roles: List<String>, jwt: String,): CreaseUserResponseDto {
    val userRecord = this
    return CreaseUserResponseDto(
        name = userRecord.name,
        username = userRecord.username,
        email = userRecord.email,
        phoneNumber = userRecord.phoneNumber,
        isSuper = userRecord.isSuper,
        roles = roles,
        ticketCount = userRecord.ticketCount,
        totalShifs = userRecord.totalShifts,
        uuid = UUID.fromString(userRecord.uuid),
        password = userRecord.password,
        jwt = jwt,
    )
}
