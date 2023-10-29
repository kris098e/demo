package project.controller

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Hej(
    val greet: String,
)
