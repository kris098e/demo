package project.controller.dto

import java.time.OffsetDateTime

data class CreateShowDto(
    val from: OffsetDateTime,
    val to: OffsetDateTime,
    val title: String,
)
