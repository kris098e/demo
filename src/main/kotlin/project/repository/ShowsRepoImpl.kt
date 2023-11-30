package project.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.generated.tables.Show.SHOW
import org.jooq.generated.tables.records.ShowRecord

@Singleton
class ShowsRepoImpl(
    private val dslContext: DSLContext
) : ShowsRepo {
    override fun fetchAllShows(): List<ShowRecord> {
        return dslContext.selectFrom(SHOW).fetch()
    }

    override fun createShow(showRecord: ShowRecord): ShowRecord {
        return dslContext.transactionResult { config ->
            val context = config.dsl()

            return@transactionResult context.insertInto(SHOW)
                .set(showRecord)
                .returning()
                .fetchOne()!!
        }
    }
}