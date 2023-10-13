package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.generated.Tables.USERS

@Singleton
class TestJooq(
        val dslContext: DSLContext
) {
    fun test(): Int {
        return dslContext.fetchCount(USERS)
    }
}