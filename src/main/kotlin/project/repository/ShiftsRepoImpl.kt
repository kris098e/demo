package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.generated.Tables.*
import org.jooq.impl.DSL
import project.repository.dto.ShiftDto
import java.time.OffsetDateTime
import java.util.*

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

        val userRoles = user.id?.let { rolesRepo.getRoles(it) } ?: emptyList()
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

    override fun updateShift(shiftUuid: UUID, userId: Long, roleId: Long,): ShiftDto? {
        return dslContext.transactionResult { conf ->
            val context = DSL.using(conf)

            context.update(SHIFT)
                .set(SHIFT.USER_ID, userId)
                .where(SHIFT.UUID.eq(shiftUuid.toString()))
                .and(SHIFT.ROLE_TYPE_ID.eq(roleId))
                .returning()
                .fetchOne()
                ?.toShiftRecord()
        }
    }

    companion object {
        private val userShowShiftJoin = SHIFT.join(SHOW)
            .on(SHIFT.SHOW_ID.eq(SHOW.ID))
            .leftJoin(USER)
            .on(SHIFT.USER_ID.eq(USER.ID))
    }
}
