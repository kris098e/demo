package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.generated.Tables.*
import org.jooq.generated.tables.records.ShiftRecord
import project.repository.dto.ShiftDto

@Singleton
class ShiftsRepoImpl(
    private val dslContext: DSLContext,
    private val rolesRepo: RolesRepo,
) : ShiftsRepo {
    override fun fetchAllShifts(): List<ShiftDto> {
        return dslContext
            .selectFrom(userShowShiftJoin)
            .fetch { it.toShiftRecord() }
    }

    fun Record.toShiftRecord(): ShiftDto {
        val shift = into(SHIFT)
        val user = into(USER)
        val show = into(SHOW)

        val roles = rolesRepo.getRoles(user.id)
        val shiftRole = rolesRepo.getRole(shift.roleTypeId)

        return ShiftDto(
            uuid = shift.uuid,
            record = user,
            show = show,
            userRoles = roles,
            shiftRole = shiftRole,
        )
    }

    override fun fetchShiftsByUserId(id: Long): List<ShiftDto> {
        return dslContext.selectFrom(userShowShiftJoin)
            .where(SHIFT.USER_ID.eq(id))
            .fetch { it.toShiftRecord() }
    }

    companion object {
        private val userShowShiftJoin = SHIFT.join(SHOW)
            .on(SHIFT.SHOW_ID.eq(SHOW.ID))
            .join(USER)
            .on(SHIFT.USER_ID.eq(USER.ID))
    }
}