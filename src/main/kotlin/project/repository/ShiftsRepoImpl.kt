package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.generated.Tables.*
import project.repository.dto.ShiftDto
import java.time.OffsetDateTime

@Singleton
class ShiftsRepoImpl(
    private val dslContext: DSLContext,
    private val rolesRepo: RolesRepo,
) : ShiftsRepo {
    override fun fetchAllShifts(from: OffsetDateTime): List<ShiftDto> {
        return dslContext
            .selectFrom(userShowShiftJoin)
            .where(SHOW.FROM.greaterOrEqual(from))
            .fetch { it.toShiftRecord() }
    }

    fun Record.toShiftRecord(): ShiftDto {
        val shift = into(SHIFT)
        val user = into(USER)
        val show = into(SHOW)

        val userRoles = rolesRepo.getRoles(user.id)
        val shiftRole = rolesRepo.getRole(shift.roleTypeId)

        return ShiftDto(
            uuid = shift.uuid,
            user = user,
            show = show,
            userRoles = userRoles,
            shiftRole = shiftRole,
        )
    }

    override fun fetchShiftsByUserId(id: Long, from: OffsetDateTime): List<ShiftDto> {
        return dslContext.selectFrom(userShowShiftJoin)
            .where(SHIFT.USER_ID.eq(id))
            .and(SHOW.FROM.greaterOrEqual(from))
            .fetch { it.toShiftRecord() }
    }

    companion object {
        private val userShowShiftJoin = SHIFT.join(SHOW)
            .on(SHIFT.SHOW_ID.eq(SHOW.ID))
            .leftJoin(USER)
            .on(SHIFT.USER_ID.eq(USER.ID))
    }
}
