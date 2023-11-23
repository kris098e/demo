package project.controller.dto

import io.micronaut.serde.annotation.Serdeable
import java.time.OffsetDateTime

@Serdeable
data class ShowsResponseDto(
    val from: OffsetDateTime,
    val to: OffsetDateTime,
    val uuid: String,
    val title: String,
)