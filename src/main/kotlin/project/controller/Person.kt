package project.controller

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Person(
        val name: String,
)
