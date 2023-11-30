package project.controller.mapper

import org.jooq.generated.tables.records.ShowRecord
import org.jooq.generated.tables.records.UserRecord
import project.controller.dto.CreaseUserResponseDto
import project.controller.dto.ShiftsResponseDto
import project.controller.dto.ShowsResponseDto
import project.controller.dto.UserResponseDto
import project.repository.dto.ShiftDto
import java.time.ZoneOffset
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

fun ShowRecord.toShowResponseDto(): ShowsResponseDto {
    val showRecord = this
    // make offsetdatetime to UTC
    return ShowsResponseDto(
        from = showRecord.from.withOffsetSameInstant(ZoneOffset.UTC),
        to = showRecord.to.withOffsetSameInstant(ZoneOffset.UTC),
        uuid = showRecord.uuid,
        title = showRecord.title,
    )
}

fun ShiftDto.toShiftsResponseDto(): ShiftsResponseDto {
    val shiftDto = this
    val user = if(shiftDto.user?.id != null)
        shiftDto.user.toUserResponseDto(roles = shiftDto.userRoles) else null
    return ShiftsResponseDto(
        uuid = shiftDto.uuid,
        role = shiftDto.shiftRole,
        show = shiftDto.show.toShowResponseDto(),
        user = user
    )
}

fun UserRecord.toUserResponseDto(roles: List<String>): UserResponseDto {
    val userRecord = this
    return UserResponseDto(
        uuid = userRecord.uuid,
        name = userRecord.name,
        email = userRecord.email,
        phoneNumber = userRecord.phoneNumber,
        roles = roles,
    )
}
