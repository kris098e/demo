package project.repository

import org.jooq.generated.tables.records.ShowRecord

interface ShowsRepo {
    fun fetchAllShows(): List<ShowRecord>
    fun createShow(showRecord: ShowRecord): ShowRecord
}