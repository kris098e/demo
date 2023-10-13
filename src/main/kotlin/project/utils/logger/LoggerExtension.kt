package project.utils.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)

fun Logger.debug(message: () -> String) {
        this.debug(message())
}

fun Logger.info(message: () -> String) {
        this.info(message())
}

fun Logger.warn(message: () -> String) {
        this.warn(message())
}