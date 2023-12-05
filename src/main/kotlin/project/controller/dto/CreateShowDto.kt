package project.controller.dto

import io.micronaut.serde.annotation.Serdeable
import java.time.OffsetDateTime

@Serdeable
data class CreateShowDto(
    val from: OffsetDateTime,
    val to: OffsetDateTime,
    val title: String,
)
